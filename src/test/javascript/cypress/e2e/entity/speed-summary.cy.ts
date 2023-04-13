import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('SpeedSummary e2e test', () => {
  const speedSummaryPageUrl = '/speed-summary';
  const speedSummaryPageUrlPattern = new RegExp('/speed-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const speedSummarySample = {};

  let speedSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/speed-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/speed-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/speed-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (speedSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/speed-summaries/${speedSummary.id}`,
      }).then(() => {
        speedSummary = undefined;
      });
    }
  });

  it('SpeedSummaries menu should load SpeedSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('speed-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SpeedSummary').should('exist');
    cy.url().should('match', speedSummaryPageUrlPattern);
  });

  describe('SpeedSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(speedSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SpeedSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/speed-summary/new$'));
        cy.getEntityCreateUpdateHeading('SpeedSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/speed-summaries',
          body: speedSummarySample,
        }).then(({ body }) => {
          speedSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/speed-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/speed-summaries?page=0&size=20>; rel="last",<http://localhost/api/speed-summaries?page=0&size=20>; rel="first"',
              },
              body: [speedSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(speedSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SpeedSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('speedSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedSummaryPageUrlPattern);
      });

      it('edit button click should load edit SpeedSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SpeedSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedSummaryPageUrlPattern);
      });

      it('edit button click should load edit SpeedSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SpeedSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of SpeedSummary', () => {
        cy.intercept('GET', '/api/speed-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('speedSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedSummaryPageUrlPattern);

        speedSummary = undefined;
      });
    });
  });

  describe('new SpeedSummary page', () => {
    beforeEach(() => {
      cy.visit(`${speedSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SpeedSummary');
    });

    it('should create an instance of SpeedSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('e-business Electrónica').should('have.value', 'e-business Electrónica');

      cy.get(`[data-cy="empresaId"]`).type('Qatari').should('have.value', 'Qatari');

      cy.get(`[data-cy="fieldAverage"]`).type('70778').should('have.value', '70778');

      cy.get(`[data-cy="fieldMax"]`).type('1736').should('have.value', '1736');

      cy.get(`[data-cy="fieldMin"]`).type('57730').should('have.value', '57730');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T13:10').blur().should('have.value', '2023-03-24T13:10');

      cy.get(`[data-cy="endTime"]`).type('2023-03-23T20:07').blur().should('have.value', '2023-03-23T20:07');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        speedSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', speedSummaryPageUrlPattern);
    });
  });
});

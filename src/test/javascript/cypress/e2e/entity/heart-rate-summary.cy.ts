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

describe('HeartRateSummary e2e test', () => {
  const heartRateSummaryPageUrl = '/heart-rate-summary';
  const heartRateSummaryPageUrlPattern = new RegExp('/heart-rate-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const heartRateSummarySample = {};

  let heartRateSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/heart-rate-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/heart-rate-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/heart-rate-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (heartRateSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/heart-rate-summaries/${heartRateSummary.id}`,
      }).then(() => {
        heartRateSummary = undefined;
      });
    }
  });

  it('HeartRateSummaries menu should load HeartRateSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('heart-rate-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('HeartRateSummary').should('exist');
    cy.url().should('match', heartRateSummaryPageUrlPattern);
  });

  describe('HeartRateSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(heartRateSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create HeartRateSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/heart-rate-summary/new$'));
        cy.getEntityCreateUpdateHeading('HeartRateSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/heart-rate-summaries',
          body: heartRateSummarySample,
        }).then(({ body }) => {
          heartRateSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/heart-rate-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/heart-rate-summaries?page=0&size=20>; rel="last",<http://localhost/api/heart-rate-summaries?page=0&size=20>; rel="first"',
              },
              body: [heartRateSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(heartRateSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details HeartRateSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('heartRateSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateSummaryPageUrlPattern);
      });

      it('edit button click should load edit HeartRateSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HeartRateSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateSummaryPageUrlPattern);
      });

      it('edit button click should load edit HeartRateSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HeartRateSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of HeartRateSummary', () => {
        cy.intercept('GET', '/api/heart-rate-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('heartRateSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateSummaryPageUrlPattern);

        heartRateSummary = undefined;
      });
    });
  });

  describe('new HeartRateSummary page', () => {
    beforeEach(() => {
      cy.visit(`${heartRateSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('HeartRateSummary');
    });

    it('should create an instance of HeartRateSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Deportes open-source').should('have.value', 'Deportes open-source');

      cy.get(`[data-cy="empresaId"]`).type('PCI').should('have.value', 'PCI');

      cy.get(`[data-cy="fieldAverage"]`).type('36340').should('have.value', '36340');

      cy.get(`[data-cy="fieldMax"]`).type('52694').should('have.value', '52694');

      cy.get(`[data-cy="fieldMin"]`).type('22256').should('have.value', '22256');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T20:10').blur().should('have.value', '2023-03-23T20:10');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T11:20').blur().should('have.value', '2023-03-24T11:20');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        heartRateSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', heartRateSummaryPageUrlPattern);
    });
  });
});

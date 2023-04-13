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

describe('HeightSummary e2e test', () => {
  const heightSummaryPageUrl = '/height-summary';
  const heightSummaryPageUrlPattern = new RegExp('/height-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const heightSummarySample = {};

  let heightSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/height-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/height-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/height-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (heightSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/height-summaries/${heightSummary.id}`,
      }).then(() => {
        heightSummary = undefined;
      });
    }
  });

  it('HeightSummaries menu should load HeightSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('height-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('HeightSummary').should('exist');
    cy.url().should('match', heightSummaryPageUrlPattern);
  });

  describe('HeightSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(heightSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create HeightSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/height-summary/new$'));
        cy.getEntityCreateUpdateHeading('HeightSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/height-summaries',
          body: heightSummarySample,
        }).then(({ body }) => {
          heightSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/height-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/height-summaries?page=0&size=20>; rel="last",<http://localhost/api/height-summaries?page=0&size=20>; rel="first"',
              },
              body: [heightSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(heightSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details HeightSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('heightSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightSummaryPageUrlPattern);
      });

      it('edit button click should load edit HeightSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HeightSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightSummaryPageUrlPattern);
      });

      it('edit button click should load edit HeightSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HeightSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of HeightSummary', () => {
        cy.intercept('GET', '/api/height-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('heightSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightSummaryPageUrlPattern);

        heightSummary = undefined;
      });
    });
  });

  describe('new HeightSummary page', () => {
    beforeEach(() => {
      cy.visit(`${heightSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('HeightSummary');
    });

    it('should create an instance of HeightSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('matrix').should('have.value', 'matrix');

      cy.get(`[data-cy="empresaId"]`).type('Hormigon ADP').should('have.value', 'Hormigon ADP');

      cy.get(`[data-cy="fieldAverage"]`).type('55160').should('have.value', '55160');

      cy.get(`[data-cy="fieldMax"]`).type('72904').should('have.value', '72904');

      cy.get(`[data-cy="fieldMin"]`).type('77983').should('have.value', '77983');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T00:28').blur().should('have.value', '2023-03-24T00:28');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T11:18').blur().should('have.value', '2023-03-24T11:18');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        heightSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', heightSummaryPageUrlPattern);
    });
  });
});

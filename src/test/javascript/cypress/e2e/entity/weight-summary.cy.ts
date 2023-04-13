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

describe('WeightSummary e2e test', () => {
  const weightSummaryPageUrl = '/weight-summary';
  const weightSummaryPageUrlPattern = new RegExp('/weight-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const weightSummarySample = {};

  let weightSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/weight-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/weight-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/weight-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (weightSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/weight-summaries/${weightSummary.id}`,
      }).then(() => {
        weightSummary = undefined;
      });
    }
  });

  it('WeightSummaries menu should load WeightSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('weight-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('WeightSummary').should('exist');
    cy.url().should('match', weightSummaryPageUrlPattern);
  });

  describe('WeightSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(weightSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create WeightSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/weight-summary/new$'));
        cy.getEntityCreateUpdateHeading('WeightSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/weight-summaries',
          body: weightSummarySample,
        }).then(({ body }) => {
          weightSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/weight-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/weight-summaries?page=0&size=20>; rel="last",<http://localhost/api/weight-summaries?page=0&size=20>; rel="first"',
              },
              body: [weightSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(weightSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details WeightSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('weightSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightSummaryPageUrlPattern);
      });

      it('edit button click should load edit WeightSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('WeightSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightSummaryPageUrlPattern);
      });

      it('edit button click should load edit WeightSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('WeightSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of WeightSummary', () => {
        cy.intercept('GET', '/api/weight-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('weightSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', weightSummaryPageUrlPattern);

        weightSummary = undefined;
      });
    });
  });

  describe('new WeightSummary page', () => {
    beforeEach(() => {
      cy.visit(`${weightSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('WeightSummary');
    });

    it('should create an instance of WeightSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Centralizado').should('have.value', 'Centralizado');

      cy.get(`[data-cy="empresaId"]`).type('Acero Sopa Blanco').should('have.value', 'Acero Sopa Blanco');

      cy.get(`[data-cy="fieldAverage"]`).type('8652').should('have.value', '8652');

      cy.get(`[data-cy="fieldMax"]`).type('95737').should('have.value', '95737');

      cy.get(`[data-cy="fieldMin"]`).type('25546').should('have.value', '25546');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T08:31').blur().should('have.value', '2023-03-24T08:31');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T02:20').blur().should('have.value', '2023-03-24T02:20');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        weightSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', weightSummaryPageUrlPattern);
    });
  });
});

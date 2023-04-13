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

describe('CaloriesBmrSummary e2e test', () => {
  const caloriesBmrSummaryPageUrl = '/calories-bmr-summary';
  const caloriesBmrSummaryPageUrlPattern = new RegExp('/calories-bmr-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const caloriesBmrSummarySample = {};

  let caloriesBmrSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/calories-bmr-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/calories-bmr-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/calories-bmr-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (caloriesBmrSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/calories-bmr-summaries/${caloriesBmrSummary.id}`,
      }).then(() => {
        caloriesBmrSummary = undefined;
      });
    }
  });

  it('CaloriesBmrSummaries menu should load CaloriesBmrSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('calories-bmr-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CaloriesBmrSummary').should('exist');
    cy.url().should('match', caloriesBmrSummaryPageUrlPattern);
  });

  describe('CaloriesBmrSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(caloriesBmrSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CaloriesBmrSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/calories-bmr-summary/new$'));
        cy.getEntityCreateUpdateHeading('CaloriesBmrSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBmrSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/calories-bmr-summaries',
          body: caloriesBmrSummarySample,
        }).then(({ body }) => {
          caloriesBmrSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/calories-bmr-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/calories-bmr-summaries?page=0&size=20>; rel="last",<http://localhost/api/calories-bmr-summaries?page=0&size=20>; rel="first"',
              },
              body: [caloriesBmrSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(caloriesBmrSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CaloriesBmrSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('caloriesBmrSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBmrSummaryPageUrlPattern);
      });

      it('edit button click should load edit CaloriesBmrSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CaloriesBmrSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBmrSummaryPageUrlPattern);
      });

      it('edit button click should load edit CaloriesBmrSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CaloriesBmrSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBmrSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of CaloriesBmrSummary', () => {
        cy.intercept('GET', '/api/calories-bmr-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('caloriesBmrSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBmrSummaryPageUrlPattern);

        caloriesBmrSummary = undefined;
      });
    });
  });

  describe('new CaloriesBmrSummary page', () => {
    beforeEach(() => {
      cy.visit(`${caloriesBmrSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CaloriesBmrSummary');
    });

    it('should create an instance of CaloriesBmrSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Adelante Principado').should('have.value', 'Adelante Principado');

      cy.get(`[data-cy="empresaId"]`).type('Madera').should('have.value', 'Madera');

      cy.get(`[data-cy="fieldAverage"]`).type('86000').should('have.value', '86000');

      cy.get(`[data-cy="fieldMax"]`).type('78518').should('have.value', '78518');

      cy.get(`[data-cy="fieldMin"]`).type('28242').should('have.value', '28242');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T00:13').blur().should('have.value', '2023-03-24T00:13');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T02:50').blur().should('have.value', '2023-03-24T02:50');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        caloriesBmrSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', caloriesBmrSummaryPageUrlPattern);
    });
  });
});

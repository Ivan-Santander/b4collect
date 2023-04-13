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

describe('NutritionSummary e2e test', () => {
  const nutritionSummaryPageUrl = '/nutrition-summary';
  const nutritionSummaryPageUrlPattern = new RegExp('/nutrition-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const nutritionSummarySample = {};

  let nutritionSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/nutrition-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/nutrition-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/nutrition-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (nutritionSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/nutrition-summaries/${nutritionSummary.id}`,
      }).then(() => {
        nutritionSummary = undefined;
      });
    }
  });

  it('NutritionSummaries menu should load NutritionSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('nutrition-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('NutritionSummary').should('exist');
    cy.url().should('match', nutritionSummaryPageUrlPattern);
  });

  describe('NutritionSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(nutritionSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create NutritionSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/nutrition-summary/new$'));
        cy.getEntityCreateUpdateHeading('NutritionSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/nutrition-summaries',
          body: nutritionSummarySample,
        }).then(({ body }) => {
          nutritionSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/nutrition-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/nutrition-summaries?page=0&size=20>; rel="last",<http://localhost/api/nutrition-summaries?page=0&size=20>; rel="first"',
              },
              body: [nutritionSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(nutritionSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details NutritionSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('nutritionSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionSummaryPageUrlPattern);
      });

      it('edit button click should load edit NutritionSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NutritionSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionSummaryPageUrlPattern);
      });

      it('edit button click should load edit NutritionSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NutritionSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of NutritionSummary', () => {
        cy.intercept('GET', '/api/nutrition-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('nutritionSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionSummaryPageUrlPattern);

        nutritionSummary = undefined;
      });
    });
  });

  describe('new NutritionSummary page', () => {
    beforeEach(() => {
      cy.visit(`${nutritionSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('NutritionSummary');
    });

    it('should create an instance of NutritionSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('THX Verde').should('have.value', 'THX Verde');

      cy.get(`[data-cy="empresaId"]`).type('e-enable eyeballs').should('have.value', 'e-enable eyeballs');

      cy.get(`[data-cy="mealType"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="nutrient"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T11:32').blur().should('have.value', '2023-03-24T11:32');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T08:30').blur().should('have.value', '2023-03-24T08:30');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        nutritionSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', nutritionSummaryPageUrlPattern);
    });
  });
});

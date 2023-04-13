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

describe('Nutrition e2e test', () => {
  const nutritionPageUrl = '/nutrition';
  const nutritionPageUrlPattern = new RegExp('/nutrition(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const nutritionSample = {};

  let nutrition;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/nutritions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/nutritions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/nutritions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (nutrition) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/nutritions/${nutrition.id}`,
      }).then(() => {
        nutrition = undefined;
      });
    }
  });

  it('Nutritions menu should load Nutritions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('nutrition');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Nutrition').should('exist');
    cy.url().should('match', nutritionPageUrlPattern);
  });

  describe('Nutrition page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(nutritionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Nutrition page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/nutrition/new$'));
        cy.getEntityCreateUpdateHeading('Nutrition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/nutritions',
          body: nutritionSample,
        }).then(({ body }) => {
          nutrition = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/nutritions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/nutritions?page=0&size=20>; rel="last",<http://localhost/api/nutritions?page=0&size=20>; rel="first"',
              },
              body: [nutrition],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(nutritionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Nutrition page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('nutrition');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionPageUrlPattern);
      });

      it('edit button click should load edit Nutrition page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Nutrition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionPageUrlPattern);
      });

      it('edit button click should load edit Nutrition page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Nutrition');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionPageUrlPattern);
      });

      it('last delete button click should delete instance of Nutrition', () => {
        cy.intercept('GET', '/api/nutritions/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('nutrition').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', nutritionPageUrlPattern);

        nutrition = undefined;
      });
    });
  });

  describe('new Nutrition page', () => {
    beforeEach(() => {
      cy.visit(`${nutritionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Nutrition');
    });

    it('should create an instance of Nutrition', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Bacon navigate').should('have.value', 'Bacon navigate');

      cy.get(`[data-cy="empresaId"]`).type('port').should('have.value', 'port');

      cy.get(`[data-cy="mealType"]`).type('60201').should('have.value', '60201');

      cy.get(`[data-cy="food"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="nutrients"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T11:29').blur().should('have.value', '2023-03-24T11:29');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        nutrition = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', nutritionPageUrlPattern);
    });
  });
});

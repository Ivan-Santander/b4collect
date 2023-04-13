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

describe('CaloriesBMR e2e test', () => {
  const caloriesBMRPageUrl = '/calories-bmr';
  const caloriesBMRPageUrlPattern = new RegExp('/calories-bmr(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const caloriesBMRSample = {};

  let caloriesBMR;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/calories-bmrs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/calories-bmrs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/calories-bmrs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (caloriesBMR) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/calories-bmrs/${caloriesBMR.id}`,
      }).then(() => {
        caloriesBMR = undefined;
      });
    }
  });

  it('CaloriesBMRS menu should load CaloriesBMRS page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('calories-bmr');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CaloriesBMR').should('exist');
    cy.url().should('match', caloriesBMRPageUrlPattern);
  });

  describe('CaloriesBMR page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(caloriesBMRPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CaloriesBMR page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/calories-bmr/new$'));
        cy.getEntityCreateUpdateHeading('CaloriesBMR');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBMRPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/calories-bmrs',
          body: caloriesBMRSample,
        }).then(({ body }) => {
          caloriesBMR = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/calories-bmrs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/calories-bmrs?page=0&size=20>; rel="last",<http://localhost/api/calories-bmrs?page=0&size=20>; rel="first"',
              },
              body: [caloriesBMR],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(caloriesBMRPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CaloriesBMR page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('caloriesBMR');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBMRPageUrlPattern);
      });

      it('edit button click should load edit CaloriesBMR page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CaloriesBMR');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBMRPageUrlPattern);
      });

      it('edit button click should load edit CaloriesBMR page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CaloriesBMR');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBMRPageUrlPattern);
      });

      it('last delete button click should delete instance of CaloriesBMR', () => {
        cy.intercept('GET', '/api/calories-bmrs/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('caloriesBMR').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesBMRPageUrlPattern);

        caloriesBMR = undefined;
      });
    });
  });

  describe('new CaloriesBMR page', () => {
    beforeEach(() => {
      cy.visit(`${caloriesBMRPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CaloriesBMR');
    });

    it('should create an instance of CaloriesBMR', () => {
      cy.get(`[data-cy="usuarioId"]`).type('convergence').should('have.value', 'convergence');

      cy.get(`[data-cy="empresaId"]`).type('Loan').should('have.value', 'Loan');

      cy.get(`[data-cy="calorias"]`).type('98016').should('have.value', '98016');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T20:01').blur().should('have.value', '2023-03-23T20:01');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T05:08').blur().should('have.value', '2023-03-24T05:08');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        caloriesBMR = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', caloriesBMRPageUrlPattern);
    });
  });
});

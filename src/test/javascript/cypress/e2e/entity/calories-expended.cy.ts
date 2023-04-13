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

describe('CaloriesExpended e2e test', () => {
  const caloriesExpendedPageUrl = '/calories-expended';
  const caloriesExpendedPageUrlPattern = new RegExp('/calories-expended(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const caloriesExpendedSample = {};

  let caloriesExpended;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/calories-expendeds+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/calories-expendeds').as('postEntityRequest');
    cy.intercept('DELETE', '/api/calories-expendeds/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (caloriesExpended) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/calories-expendeds/${caloriesExpended.id}`,
      }).then(() => {
        caloriesExpended = undefined;
      });
    }
  });

  it('CaloriesExpendeds menu should load CaloriesExpendeds page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('calories-expended');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CaloriesExpended').should('exist');
    cy.url().should('match', caloriesExpendedPageUrlPattern);
  });

  describe('CaloriesExpended page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(caloriesExpendedPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CaloriesExpended page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/calories-expended/new$'));
        cy.getEntityCreateUpdateHeading('CaloriesExpended');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesExpendedPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/calories-expendeds',
          body: caloriesExpendedSample,
        }).then(({ body }) => {
          caloriesExpended = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/calories-expendeds+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/calories-expendeds?page=0&size=20>; rel="last",<http://localhost/api/calories-expendeds?page=0&size=20>; rel="first"',
              },
              body: [caloriesExpended],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(caloriesExpendedPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CaloriesExpended page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('caloriesExpended');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesExpendedPageUrlPattern);
      });

      it('edit button click should load edit CaloriesExpended page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CaloriesExpended');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesExpendedPageUrlPattern);
      });

      it('edit button click should load edit CaloriesExpended page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CaloriesExpended');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesExpendedPageUrlPattern);
      });

      it('last delete button click should delete instance of CaloriesExpended', () => {
        cy.intercept('GET', '/api/calories-expendeds/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('caloriesExpended').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', caloriesExpendedPageUrlPattern);

        caloriesExpended = undefined;
      });
    });
  });

  describe('new CaloriesExpended page', () => {
    beforeEach(() => {
      cy.visit(`${caloriesExpendedPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CaloriesExpended');
    });

    it('should create an instance of CaloriesExpended', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Berkshire').should('have.value', 'Berkshire');

      cy.get(`[data-cy="empresaId"]`).type('Rojo Inteligente').should('have.value', 'Rojo Inteligente');

      cy.get(`[data-cy="calorias"]`).type('52290').should('have.value', '52290');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T21:13').blur().should('have.value', '2023-03-23T21:13');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T13:31').blur().should('have.value', '2023-03-24T13:31');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        caloriesExpended = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', caloriesExpendedPageUrlPattern);
    });
  });
});

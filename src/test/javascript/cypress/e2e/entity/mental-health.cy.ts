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

describe('MentalHealth e2e test', () => {
  const mentalHealthPageUrl = '/mental-health';
  const mentalHealthPageUrlPattern = new RegExp('/mental-health(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const mentalHealthSample = {};

  let mentalHealth;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/mental-healths+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/mental-healths').as('postEntityRequest');
    cy.intercept('DELETE', '/api/mental-healths/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (mentalHealth) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/mental-healths/${mentalHealth.id}`,
      }).then(() => {
        mentalHealth = undefined;
      });
    }
  });

  it('MentalHealths menu should load MentalHealths page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('mental-health');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MentalHealth').should('exist');
    cy.url().should('match', mentalHealthPageUrlPattern);
  });

  describe('MentalHealth page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(mentalHealthPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MentalHealth page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/mental-health/new$'));
        cy.getEntityCreateUpdateHeading('MentalHealth');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/mental-healths',
          body: mentalHealthSample,
        }).then(({ body }) => {
          mentalHealth = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/mental-healths+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/mental-healths?page=0&size=20>; rel="last",<http://localhost/api/mental-healths?page=0&size=20>; rel="first"',
              },
              body: [mentalHealth],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(mentalHealthPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MentalHealth page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('mentalHealth');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthPageUrlPattern);
      });

      it('edit button click should load edit MentalHealth page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MentalHealth');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthPageUrlPattern);
      });

      it('edit button click should load edit MentalHealth page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MentalHealth');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthPageUrlPattern);
      });

      it('last delete button click should delete instance of MentalHealth', () => {
        cy.intercept('GET', '/api/mental-healths/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('mentalHealth').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthPageUrlPattern);

        mentalHealth = undefined;
      });
    });
  });

  describe('new MentalHealth page', () => {
    beforeEach(() => {
      cy.visit(`${mentalHealthPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MentalHealth');
    });

    it('should create an instance of MentalHealth', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Actualizable producto Queso').should('have.value', 'Actualizable producto Queso');

      cy.get(`[data-cy="empresaId"]`).type('Uganda brand synthesize').should('have.value', 'Uganda brand synthesize');

      cy.get(`[data-cy="emotionDescription"]`).type('Oficial e-business').should('have.value', 'Oficial e-business');

      cy.get(`[data-cy="emotionValue"]`).type('94042').should('have.value', '94042');

      cy.get(`[data-cy="startDate"]`).type('2023-04-09T22:55').blur().should('have.value', '2023-04-09T22:55');

      cy.get(`[data-cy="endDate"]`).type('2023-04-10T11:46').blur().should('have.value', '2023-04-10T11:46');

      cy.get(`[data-cy="mentalHealthScore"]`).type('33288').should('have.value', '33288');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        mentalHealth = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', mentalHealthPageUrlPattern);
    });
  });
});

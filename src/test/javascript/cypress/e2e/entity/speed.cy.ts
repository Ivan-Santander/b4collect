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

describe('Speed e2e test', () => {
  const speedPageUrl = '/speed';
  const speedPageUrlPattern = new RegExp('/speed(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const speedSample = {};

  let speed;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/speeds+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/speeds').as('postEntityRequest');
    cy.intercept('DELETE', '/api/speeds/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (speed) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/speeds/${speed.id}`,
      }).then(() => {
        speed = undefined;
      });
    }
  });

  it('Speeds menu should load Speeds page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('speed');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Speed').should('exist');
    cy.url().should('match', speedPageUrlPattern);
  });

  describe('Speed page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(speedPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Speed page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/speed/new$'));
        cy.getEntityCreateUpdateHeading('Speed');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/speeds',
          body: speedSample,
        }).then(({ body }) => {
          speed = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/speeds+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/speeds?page=0&size=20>; rel="last",<http://localhost/api/speeds?page=0&size=20>; rel="first"',
              },
              body: [speed],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(speedPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Speed page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('speed');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedPageUrlPattern);
      });

      it('edit button click should load edit Speed page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Speed');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedPageUrlPattern);
      });

      it('edit button click should load edit Speed page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Speed');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedPageUrlPattern);
      });

      it('last delete button click should delete instance of Speed', () => {
        cy.intercept('GET', '/api/speeds/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('speed').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', speedPageUrlPattern);

        speed = undefined;
      });
    });
  });

  describe('new Speed page', () => {
    beforeEach(() => {
      cy.visit(`${speedPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Speed');
    });

    it('should create an instance of Speed', () => {
      cy.get(`[data-cy="usuarioId"]`).type('fritas').should('have.value', 'fritas');

      cy.get(`[data-cy="empresaId"]`).type('card Navarra parse').should('have.value', 'card Navarra parse');

      cy.get(`[data-cy="speed"]`).type('99764').should('have.value', '99764');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T04:28').blur().should('have.value', '2023-03-24T04:28');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        speed = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', speedPageUrlPattern);
    });
  });
});

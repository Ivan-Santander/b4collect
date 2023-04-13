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

describe('Height e2e test', () => {
  const heightPageUrl = '/height';
  const heightPageUrlPattern = new RegExp('/height(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const heightSample = {};

  let height;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/heights+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/heights').as('postEntityRequest');
    cy.intercept('DELETE', '/api/heights/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (height) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/heights/${height.id}`,
      }).then(() => {
        height = undefined;
      });
    }
  });

  it('Heights menu should load Heights page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('height');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Height').should('exist');
    cy.url().should('match', heightPageUrlPattern);
  });

  describe('Height page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(heightPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Height page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/height/new$'));
        cy.getEntityCreateUpdateHeading('Height');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/heights',
          body: heightSample,
        }).then(({ body }) => {
          height = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/heights+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/heights?page=0&size=20>; rel="last",<http://localhost/api/heights?page=0&size=20>; rel="first"',
              },
              body: [height],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(heightPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Height page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('height');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightPageUrlPattern);
      });

      it('edit button click should load edit Height page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Height');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightPageUrlPattern);
      });

      it('edit button click should load edit Height page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Height');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightPageUrlPattern);
      });

      it('last delete button click should delete instance of Height', () => {
        cy.intercept('GET', '/api/heights/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('height').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heightPageUrlPattern);

        height = undefined;
      });
    });
  });

  describe('new Height page', () => {
    beforeEach(() => {
      cy.visit(`${heightPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Height');
    });

    it('should create an instance of Height', () => {
      cy.get(`[data-cy="usuarioId"]`).type('withdrawal Bedfordshire').should('have.value', 'withdrawal Bedfordshire');

      cy.get(`[data-cy="empresaId"]`).type('robust').should('have.value', 'robust');

      cy.get(`[data-cy="fieldHeight"]`).type('6874').should('have.value', '6874');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T03:32').blur().should('have.value', '2023-03-24T03:32');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        height = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', heightPageUrlPattern);
    });
  });
});

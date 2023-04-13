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

describe('BodyFatPercentage e2e test', () => {
  const bodyFatPercentagePageUrl = '/body-fat-percentage';
  const bodyFatPercentagePageUrlPattern = new RegExp('/body-fat-percentage(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bodyFatPercentageSample = {};

  let bodyFatPercentage;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/body-fat-percentages+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/body-fat-percentages').as('postEntityRequest');
    cy.intercept('DELETE', '/api/body-fat-percentages/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bodyFatPercentage) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/body-fat-percentages/${bodyFatPercentage.id}`,
      }).then(() => {
        bodyFatPercentage = undefined;
      });
    }
  });

  it('BodyFatPercentages menu should load BodyFatPercentages page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('body-fat-percentage');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BodyFatPercentage').should('exist');
    cy.url().should('match', bodyFatPercentagePageUrlPattern);
  });

  describe('BodyFatPercentage page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bodyFatPercentagePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BodyFatPercentage page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/body-fat-percentage/new$'));
        cy.getEntityCreateUpdateHeading('BodyFatPercentage');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentagePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/body-fat-percentages',
          body: bodyFatPercentageSample,
        }).then(({ body }) => {
          bodyFatPercentage = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/body-fat-percentages+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/body-fat-percentages?page=0&size=20>; rel="last",<http://localhost/api/body-fat-percentages?page=0&size=20>; rel="first"',
              },
              body: [bodyFatPercentage],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bodyFatPercentagePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BodyFatPercentage page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bodyFatPercentage');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentagePageUrlPattern);
      });

      it('edit button click should load edit BodyFatPercentage page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BodyFatPercentage');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentagePageUrlPattern);
      });

      it('edit button click should load edit BodyFatPercentage page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BodyFatPercentage');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentagePageUrlPattern);
      });

      it('last delete button click should delete instance of BodyFatPercentage', () => {
        cy.intercept('GET', '/api/body-fat-percentages/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('bodyFatPercentage').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentagePageUrlPattern);

        bodyFatPercentage = undefined;
      });
    });
  });

  describe('new BodyFatPercentage page', () => {
    beforeEach(() => {
      cy.visit(`${bodyFatPercentagePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BodyFatPercentage');
    });

    it('should create an instance of BodyFatPercentage', () => {
      cy.get(`[data-cy="usuarioId"]`).type('e-business fritas').should('have.value', 'e-business fritas');

      cy.get(`[data-cy="empresaId"]`).type('deposit innovative').should('have.value', 'deposit innovative');

      cy.get(`[data-cy="fieldPorcentage"]`).type('22227').should('have.value', '22227');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T09:48').blur().should('have.value', '2023-03-24T09:48');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        bodyFatPercentage = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', bodyFatPercentagePageUrlPattern);
    });
  });
});

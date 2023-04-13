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

describe('BodyTemperature e2e test', () => {
  const bodyTemperaturePageUrl = '/body-temperature';
  const bodyTemperaturePageUrlPattern = new RegExp('/body-temperature(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bodyTemperatureSample = {};

  let bodyTemperature;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/body-temperatures+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/body-temperatures').as('postEntityRequest');
    cy.intercept('DELETE', '/api/body-temperatures/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bodyTemperature) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/body-temperatures/${bodyTemperature.id}`,
      }).then(() => {
        bodyTemperature = undefined;
      });
    }
  });

  it('BodyTemperatures menu should load BodyTemperatures page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('body-temperature');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BodyTemperature').should('exist');
    cy.url().should('match', bodyTemperaturePageUrlPattern);
  });

  describe('BodyTemperature page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bodyTemperaturePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BodyTemperature page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/body-temperature/new$'));
        cy.getEntityCreateUpdateHeading('BodyTemperature');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyTemperaturePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/body-temperatures',
          body: bodyTemperatureSample,
        }).then(({ body }) => {
          bodyTemperature = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/body-temperatures+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/body-temperatures?page=0&size=20>; rel="last",<http://localhost/api/body-temperatures?page=0&size=20>; rel="first"',
              },
              body: [bodyTemperature],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bodyTemperaturePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BodyTemperature page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bodyTemperature');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyTemperaturePageUrlPattern);
      });

      it('edit button click should load edit BodyTemperature page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BodyTemperature');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyTemperaturePageUrlPattern);
      });

      it('edit button click should load edit BodyTemperature page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BodyTemperature');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyTemperaturePageUrlPattern);
      });

      it('last delete button click should delete instance of BodyTemperature', () => {
        cy.intercept('GET', '/api/body-temperatures/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('bodyTemperature').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyTemperaturePageUrlPattern);

        bodyTemperature = undefined;
      });
    });
  });

  describe('new BodyTemperature page', () => {
    beforeEach(() => {
      cy.visit(`${bodyTemperaturePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BodyTemperature');
    });

    it('should create an instance of BodyTemperature', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Austria Inversor').should('have.value', 'Austria Inversor');

      cy.get(`[data-cy="empresaId"]`).type('Solar Decoración Borders').should('have.value', 'Solar Decoración Borders');

      cy.get(`[data-cy="fieldBodyTemperature"]`).type('8829').should('have.value', '8829');

      cy.get(`[data-cy="fieldBodyTemperatureMeasureLocation"]`).type('36448').should('have.value', '36448');

      cy.get(`[data-cy="endTime"]`).type('2023-03-23T21:47').blur().should('have.value', '2023-03-23T21:47');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        bodyTemperature = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', bodyTemperaturePageUrlPattern);
    });
  });
});

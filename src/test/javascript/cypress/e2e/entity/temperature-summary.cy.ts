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

describe('TemperatureSummary e2e test', () => {
  const temperatureSummaryPageUrl = '/temperature-summary';
  const temperatureSummaryPageUrlPattern = new RegExp('/temperature-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const temperatureSummarySample = {};

  let temperatureSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/temperature-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/temperature-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/temperature-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (temperatureSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/temperature-summaries/${temperatureSummary.id}`,
      }).then(() => {
        temperatureSummary = undefined;
      });
    }
  });

  it('TemperatureSummaries menu should load TemperatureSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('temperature-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('TemperatureSummary').should('exist');
    cy.url().should('match', temperatureSummaryPageUrlPattern);
  });

  describe('TemperatureSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(temperatureSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create TemperatureSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/temperature-summary/new$'));
        cy.getEntityCreateUpdateHeading('TemperatureSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', temperatureSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/temperature-summaries',
          body: temperatureSummarySample,
        }).then(({ body }) => {
          temperatureSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/temperature-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/temperature-summaries?page=0&size=20>; rel="last",<http://localhost/api/temperature-summaries?page=0&size=20>; rel="first"',
              },
              body: [temperatureSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(temperatureSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details TemperatureSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('temperatureSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', temperatureSummaryPageUrlPattern);
      });

      it('edit button click should load edit TemperatureSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TemperatureSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', temperatureSummaryPageUrlPattern);
      });

      it('edit button click should load edit TemperatureSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('TemperatureSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', temperatureSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of TemperatureSummary', () => {
        cy.intercept('GET', '/api/temperature-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('temperatureSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', temperatureSummaryPageUrlPattern);

        temperatureSummary = undefined;
      });
    });
  });

  describe('new TemperatureSummary page', () => {
    beforeEach(() => {
      cy.visit(`${temperatureSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('TemperatureSummary');
    });

    it('should create an instance of TemperatureSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('desafío seamless indexing').should('have.value', 'desafío seamless indexing');

      cy.get(`[data-cy="empresaId"]`).type('utilización hack synthesizing').should('have.value', 'utilización hack synthesizing');

      cy.get(`[data-cy="fieldAverage"]`).type('5592').should('have.value', '5592');

      cy.get(`[data-cy="fieldMax"]`).type('13412').should('have.value', '13412');

      cy.get(`[data-cy="fieldMin"]`).type('94330').should('have.value', '94330');

      cy.get(`[data-cy="measurementLocation"]`).type('5095').should('have.value', '5095');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T11:03').blur().should('have.value', '2023-03-24T11:03');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T03:53').blur().should('have.value', '2023-03-24T03:53');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        temperatureSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', temperatureSummaryPageUrlPattern);
    });
  });
});

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

describe('PowerSummary e2e test', () => {
  const powerSummaryPageUrl = '/power-summary';
  const powerSummaryPageUrlPattern = new RegExp('/power-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const powerSummarySample = {};

  let powerSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/power-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/power-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/power-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (powerSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/power-summaries/${powerSummary.id}`,
      }).then(() => {
        powerSummary = undefined;
      });
    }
  });

  it('PowerSummaries menu should load PowerSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('power-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PowerSummary').should('exist');
    cy.url().should('match', powerSummaryPageUrlPattern);
  });

  describe('PowerSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(powerSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PowerSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/power-summary/new$'));
        cy.getEntityCreateUpdateHeading('PowerSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', powerSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/power-summaries',
          body: powerSummarySample,
        }).then(({ body }) => {
          powerSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/power-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/power-summaries?page=0&size=20>; rel="last",<http://localhost/api/power-summaries?page=0&size=20>; rel="first"',
              },
              body: [powerSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(powerSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PowerSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('powerSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', powerSummaryPageUrlPattern);
      });

      it('edit button click should load edit PowerSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PowerSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', powerSummaryPageUrlPattern);
      });

      it('edit button click should load edit PowerSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PowerSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', powerSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of PowerSummary', () => {
        cy.intercept('GET', '/api/power-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('powerSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', powerSummaryPageUrlPattern);

        powerSummary = undefined;
      });
    });
  });

  describe('new PowerSummary page', () => {
    beforeEach(() => {
      cy.visit(`${powerSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PowerSummary');
    });

    it('should create an instance of PowerSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('CSS Blanco Galicia').should('have.value', 'CSS Blanco Galicia');

      cy.get(`[data-cy="empresaId"]`).type('Sorprendente wireless Ejecutivo').should('have.value', 'Sorprendente wireless Ejecutivo');

      cy.get(`[data-cy="fieldAverage"]`).type('349').should('have.value', '349');

      cy.get(`[data-cy="fieldMax"]`).type('58622').should('have.value', '58622');

      cy.get(`[data-cy="fieldMin"]`).type('9723').should('have.value', '9723');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T01:09').blur().should('have.value', '2023-03-24T01:09');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T10:47').blur().should('have.value', '2023-03-24T10:47');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        powerSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', powerSummaryPageUrlPattern);
    });
  });
});

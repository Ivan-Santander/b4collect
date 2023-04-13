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

describe('BloodPressureSummary e2e test', () => {
  const bloodPressureSummaryPageUrl = '/blood-pressure-summary';
  const bloodPressureSummaryPageUrlPattern = new RegExp('/blood-pressure-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bloodPressureSummarySample = {};

  let bloodPressureSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/blood-pressure-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/blood-pressure-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/blood-pressure-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bloodPressureSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/blood-pressure-summaries/${bloodPressureSummary.id}`,
      }).then(() => {
        bloodPressureSummary = undefined;
      });
    }
  });

  it('BloodPressureSummaries menu should load BloodPressureSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blood-pressure-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BloodPressureSummary').should('exist');
    cy.url().should('match', bloodPressureSummaryPageUrlPattern);
  });

  describe('BloodPressureSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bloodPressureSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BloodPressureSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blood-pressure-summary/new$'));
        cy.getEntityCreateUpdateHeading('BloodPressureSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodPressureSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/blood-pressure-summaries',
          body: bloodPressureSummarySample,
        }).then(({ body }) => {
          bloodPressureSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/blood-pressure-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/blood-pressure-summaries?page=0&size=20>; rel="last",<http://localhost/api/blood-pressure-summaries?page=0&size=20>; rel="first"',
              },
              body: [bloodPressureSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bloodPressureSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BloodPressureSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bloodPressureSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodPressureSummaryPageUrlPattern);
      });

      it('edit button click should load edit BloodPressureSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BloodPressureSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodPressureSummaryPageUrlPattern);
      });

      it('edit button click should load edit BloodPressureSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BloodPressureSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodPressureSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of BloodPressureSummary', () => {
        cy.intercept('GET', '/api/blood-pressure-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('bloodPressureSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodPressureSummaryPageUrlPattern);

        bloodPressureSummary = undefined;
      });
    });
  });

  describe('new BloodPressureSummary page', () => {
    beforeEach(() => {
      cy.visit(`${bloodPressureSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BloodPressureSummary');
    });

    it('should create an instance of BloodPressureSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('functionalities').should('have.value', 'functionalities');

      cy.get(`[data-cy="empresaId"]`).type('Genérico Guapa País').should('have.value', 'Genérico Guapa País');

      cy.get(`[data-cy="fieldSistolicAverage"]`).type('29015').should('have.value', '29015');

      cy.get(`[data-cy="fieldSistolicMax"]`).type('37791').should('have.value', '37791');

      cy.get(`[data-cy="fieldSistolicMin"]`).type('44415').should('have.value', '44415');

      cy.get(`[data-cy="fieldDiasatolicAverage"]`).type('97478').should('have.value', '97478');

      cy.get(`[data-cy="fieldDiastolicMax"]`).type('64920').should('have.value', '64920');

      cy.get(`[data-cy="fieldDiastolicMin"]`).type('32219').should('have.value', '32219');

      cy.get(`[data-cy="bodyPosition"]`).type('2974').should('have.value', '2974');

      cy.get(`[data-cy="measurementLocation"]`).type('49830').should('have.value', '49830');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T19:24').blur().should('have.value', '2023-03-23T19:24');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T01:02').blur().should('have.value', '2023-03-24T01:02');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        bloodPressureSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', bloodPressureSummaryPageUrlPattern);
    });
  });
});

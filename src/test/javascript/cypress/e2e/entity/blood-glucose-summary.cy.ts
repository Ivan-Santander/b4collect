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

describe('BloodGlucoseSummary e2e test', () => {
  const bloodGlucoseSummaryPageUrl = '/blood-glucose-summary';
  const bloodGlucoseSummaryPageUrlPattern = new RegExp('/blood-glucose-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bloodGlucoseSummarySample = {};

  let bloodGlucoseSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/blood-glucose-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/blood-glucose-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/blood-glucose-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bloodGlucoseSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/blood-glucose-summaries/${bloodGlucoseSummary.id}`,
      }).then(() => {
        bloodGlucoseSummary = undefined;
      });
    }
  });

  it('BloodGlucoseSummaries menu should load BloodGlucoseSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blood-glucose-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BloodGlucoseSummary').should('exist');
    cy.url().should('match', bloodGlucoseSummaryPageUrlPattern);
  });

  describe('BloodGlucoseSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bloodGlucoseSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BloodGlucoseSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blood-glucose-summary/new$'));
        cy.getEntityCreateUpdateHeading('BloodGlucoseSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucoseSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/blood-glucose-summaries',
          body: bloodGlucoseSummarySample,
        }).then(({ body }) => {
          bloodGlucoseSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/blood-glucose-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/blood-glucose-summaries?page=0&size=20>; rel="last",<http://localhost/api/blood-glucose-summaries?page=0&size=20>; rel="first"',
              },
              body: [bloodGlucoseSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bloodGlucoseSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BloodGlucoseSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bloodGlucoseSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucoseSummaryPageUrlPattern);
      });

      it('edit button click should load edit BloodGlucoseSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BloodGlucoseSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucoseSummaryPageUrlPattern);
      });

      it('edit button click should load edit BloodGlucoseSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BloodGlucoseSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucoseSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of BloodGlucoseSummary', () => {
        cy.intercept('GET', '/api/blood-glucose-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('bloodGlucoseSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucoseSummaryPageUrlPattern);

        bloodGlucoseSummary = undefined;
      });
    });
  });

  describe('new BloodGlucoseSummary page', () => {
    beforeEach(() => {
      cy.visit(`${bloodGlucoseSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BloodGlucoseSummary');
    });

    it('should create an instance of BloodGlucoseSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('generación drive SSL').should('have.value', 'generación drive SSL');

      cy.get(`[data-cy="empresaId"]`).type('Extendido FTP').should('have.value', 'Extendido FTP');

      cy.get(`[data-cy="fieldAverage"]`).type('54410').should('have.value', '54410');

      cy.get(`[data-cy="fieldMax"]`).type('39808').should('have.value', '39808');

      cy.get(`[data-cy="fieldMin"]`).type('754').should('have.value', '754');

      cy.get(`[data-cy="intervalFood"]`).type('55199').should('have.value', '55199');

      cy.get(`[data-cy="mealType"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="relationTemporalSleep"]`).type('31085').should('have.value', '31085');

      cy.get(`[data-cy="sampleSource"]`).type('95949').should('have.value', '95949');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T08:34').blur().should('have.value', '2023-03-24T08:34');

      cy.get(`[data-cy="endTime"]`).type('2023-03-23T14:56').blur().should('have.value', '2023-03-23T14:56');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        bloodGlucoseSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', bloodGlucoseSummaryPageUrlPattern);
    });
  });
});

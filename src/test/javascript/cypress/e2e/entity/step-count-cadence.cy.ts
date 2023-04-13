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

describe('StepCountCadence e2e test', () => {
  const stepCountCadencePageUrl = '/step-count-cadence';
  const stepCountCadencePageUrlPattern = new RegExp('/step-count-cadence(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const stepCountCadenceSample = {};

  let stepCountCadence;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/step-count-cadences+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/step-count-cadences').as('postEntityRequest');
    cy.intercept('DELETE', '/api/step-count-cadences/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (stepCountCadence) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/step-count-cadences/${stepCountCadence.id}`,
      }).then(() => {
        stepCountCadence = undefined;
      });
    }
  });

  it('StepCountCadences menu should load StepCountCadences page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('step-count-cadence');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('StepCountCadence').should('exist');
    cy.url().should('match', stepCountCadencePageUrlPattern);
  });

  describe('StepCountCadence page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(stepCountCadencePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create StepCountCadence page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/step-count-cadence/new$'));
        cy.getEntityCreateUpdateHeading('StepCountCadence');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountCadencePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/step-count-cadences',
          body: stepCountCadenceSample,
        }).then(({ body }) => {
          stepCountCadence = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/step-count-cadences+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/step-count-cadences?page=0&size=20>; rel="last",<http://localhost/api/step-count-cadences?page=0&size=20>; rel="first"',
              },
              body: [stepCountCadence],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(stepCountCadencePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details StepCountCadence page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('stepCountCadence');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountCadencePageUrlPattern);
      });

      it('edit button click should load edit StepCountCadence page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StepCountCadence');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountCadencePageUrlPattern);
      });

      it('edit button click should load edit StepCountCadence page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StepCountCadence');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountCadencePageUrlPattern);
      });

      it('last delete button click should delete instance of StepCountCadence', () => {
        cy.intercept('GET', '/api/step-count-cadences/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('stepCountCadence').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountCadencePageUrlPattern);

        stepCountCadence = undefined;
      });
    });
  });

  describe('new StepCountCadence page', () => {
    beforeEach(() => {
      cy.visit(`${stepCountCadencePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('StepCountCadence');
    });

    it('should create an instance of StepCountCadence', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Ergonómico').should('have.value', 'Ergonómico');

      cy.get(`[data-cy="empresaId"]`).type('primary Optimización Kroon').should('have.value', 'primary Optimización Kroon');

      cy.get(`[data-cy="rpm"]`).type('76835').should('have.value', '76835');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T22:10').blur().should('have.value', '2023-03-23T22:10');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T08:06').blur().should('have.value', '2023-03-24T08:06');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        stepCountCadence = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', stepCountCadencePageUrlPattern);
    });
  });
});

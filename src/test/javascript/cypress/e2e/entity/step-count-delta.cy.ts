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

describe('StepCountDelta e2e test', () => {
  const stepCountDeltaPageUrl = '/step-count-delta';
  const stepCountDeltaPageUrlPattern = new RegExp('/step-count-delta(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const stepCountDeltaSample = {};

  let stepCountDelta;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/step-count-deltas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/step-count-deltas').as('postEntityRequest');
    cy.intercept('DELETE', '/api/step-count-deltas/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (stepCountDelta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/step-count-deltas/${stepCountDelta.id}`,
      }).then(() => {
        stepCountDelta = undefined;
      });
    }
  });

  it('StepCountDeltas menu should load StepCountDeltas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('step-count-delta');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('StepCountDelta').should('exist');
    cy.url().should('match', stepCountDeltaPageUrlPattern);
  });

  describe('StepCountDelta page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(stepCountDeltaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create StepCountDelta page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/step-count-delta/new$'));
        cy.getEntityCreateUpdateHeading('StepCountDelta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountDeltaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/step-count-deltas',
          body: stepCountDeltaSample,
        }).then(({ body }) => {
          stepCountDelta = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/step-count-deltas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/step-count-deltas?page=0&size=20>; rel="last",<http://localhost/api/step-count-deltas?page=0&size=20>; rel="first"',
              },
              body: [stepCountDelta],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(stepCountDeltaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details StepCountDelta page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('stepCountDelta');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountDeltaPageUrlPattern);
      });

      it('edit button click should load edit StepCountDelta page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StepCountDelta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountDeltaPageUrlPattern);
      });

      it('edit button click should load edit StepCountDelta page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('StepCountDelta');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountDeltaPageUrlPattern);
      });

      it('last delete button click should delete instance of StepCountDelta', () => {
        cy.intercept('GET', '/api/step-count-deltas/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('stepCountDelta').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', stepCountDeltaPageUrlPattern);

        stepCountDelta = undefined;
      });
    });
  });

  describe('new StepCountDelta page', () => {
    beforeEach(() => {
      cy.visit(`${stepCountDeltaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('StepCountDelta');
    });

    it('should create an instance of StepCountDelta', () => {
      cy.get(`[data-cy="usuarioId"]`).type('haptic no-volátil').should('have.value', 'haptic no-volátil');

      cy.get(`[data-cy="empresaId"]`).type('optical Explanada').should('have.value', 'optical Explanada');

      cy.get(`[data-cy="steps"]`).type('35325').should('have.value', '35325');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T18:44').blur().should('have.value', '2023-03-23T18:44');

      cy.get(`[data-cy="endTime"]`).type('2023-03-23T22:19').blur().should('have.value', '2023-03-23T22:19');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        stepCountDelta = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', stepCountDeltaPageUrlPattern);
    });
  });
});

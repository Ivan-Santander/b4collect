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

describe('ActivityExercise e2e test', () => {
  const activityExercisePageUrl = '/activity-exercise';
  const activityExercisePageUrlPattern = new RegExp('/activity-exercise(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const activityExerciseSample = {};

  let activityExercise;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/activity-exercises+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/activity-exercises').as('postEntityRequest');
    cy.intercept('DELETE', '/api/activity-exercises/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (activityExercise) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/activity-exercises/${activityExercise.id}`,
      }).then(() => {
        activityExercise = undefined;
      });
    }
  });

  it('ActivityExercises menu should load ActivityExercises page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('activity-exercise');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ActivityExercise').should('exist');
    cy.url().should('match', activityExercisePageUrlPattern);
  });

  describe('ActivityExercise page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(activityExercisePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ActivityExercise page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/activity-exercise/new$'));
        cy.getEntityCreateUpdateHeading('ActivityExercise');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activityExercisePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/activity-exercises',
          body: activityExerciseSample,
        }).then(({ body }) => {
          activityExercise = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/activity-exercises+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/activity-exercises?page=0&size=20>; rel="last",<http://localhost/api/activity-exercises?page=0&size=20>; rel="first"',
              },
              body: [activityExercise],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(activityExercisePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ActivityExercise page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('activityExercise');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activityExercisePageUrlPattern);
      });

      it('edit button click should load edit ActivityExercise page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ActivityExercise');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activityExercisePageUrlPattern);
      });

      it('edit button click should load edit ActivityExercise page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ActivityExercise');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activityExercisePageUrlPattern);
      });

      it('last delete button click should delete instance of ActivityExercise', () => {
        cy.intercept('GET', '/api/activity-exercises/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('activityExercise').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activityExercisePageUrlPattern);

        activityExercise = undefined;
      });
    });
  });

  describe('new ActivityExercise page', () => {
    beforeEach(() => {
      cy.visit(`${activityExercisePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ActivityExercise');
    });

    it('should create an instance of ActivityExercise', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Cataluña Riera').should('have.value', 'Cataluña Riera');

      cy.get(`[data-cy="empresaId"]`).type('Investment').should('have.value', 'Investment');

      cy.get(`[data-cy="exercise"]`).type('intermedia microchip').should('have.value', 'intermedia microchip');

      cy.get(`[data-cy="repetitions"]`).type('21441').should('have.value', '21441');

      cy.get(`[data-cy="typeResistence"]`).type('Gerente payment Avon').should('have.value', 'Gerente payment Avon');

      cy.get(`[data-cy="resistenceKg"]`).type('93171').should('have.value', '93171');

      cy.get(`[data-cy="duration"]`).type('59409').should('have.value', '59409');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T09:39').blur().should('have.value', '2023-03-24T09:39');

      cy.get(`[data-cy="endTime"]`).type('2023-03-23T15:13').blur().should('have.value', '2023-03-23T15:13');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        activityExercise = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', activityExercisePageUrlPattern);
    });
  });
});

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

describe('BloodGlucose e2e test', () => {
  const bloodGlucosePageUrl = '/blood-glucose';
  const bloodGlucosePageUrlPattern = new RegExp('/blood-glucose(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bloodGlucoseSample = {};

  let bloodGlucose;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/blood-glucoses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/blood-glucoses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/blood-glucoses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bloodGlucose) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/blood-glucoses/${bloodGlucose.id}`,
      }).then(() => {
        bloodGlucose = undefined;
      });
    }
  });

  it('BloodGlucoses menu should load BloodGlucoses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('blood-glucose');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BloodGlucose').should('exist');
    cy.url().should('match', bloodGlucosePageUrlPattern);
  });

  describe('BloodGlucose page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bloodGlucosePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BloodGlucose page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/blood-glucose/new$'));
        cy.getEntityCreateUpdateHeading('BloodGlucose');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucosePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/blood-glucoses',
          body: bloodGlucoseSample,
        }).then(({ body }) => {
          bloodGlucose = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/blood-glucoses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/blood-glucoses?page=0&size=20>; rel="last",<http://localhost/api/blood-glucoses?page=0&size=20>; rel="first"',
              },
              body: [bloodGlucose],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bloodGlucosePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BloodGlucose page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bloodGlucose');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucosePageUrlPattern);
      });

      it('edit button click should load edit BloodGlucose page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BloodGlucose');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucosePageUrlPattern);
      });

      it('edit button click should load edit BloodGlucose page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BloodGlucose');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucosePageUrlPattern);
      });

      it('last delete button click should delete instance of BloodGlucose', () => {
        cy.intercept('GET', '/api/blood-glucoses/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('bloodGlucose').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bloodGlucosePageUrlPattern);

        bloodGlucose = undefined;
      });
    });
  });

  describe('new BloodGlucose page', () => {
    beforeEach(() => {
      cy.visit(`${bloodGlucosePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BloodGlucose');
    });

    it('should create an instance of BloodGlucose', () => {
      cy.get(`[data-cy="usuarioId"]`).type('European port feed').should('have.value', 'European port feed');

      cy.get(`[data-cy="empresaId"]`).type('Granito Riera Parque').should('have.value', 'Granito Riera Parque');

      cy.get(`[data-cy="fieldBloodGlucoseLevel"]`).type('77017').should('have.value', '77017');

      cy.get(`[data-cy="fieldTemporalRelationToMeal"]`).type('8217').should('have.value', '8217');

      cy.get(`[data-cy="fieldMealType"]`).type('57531').should('have.value', '57531');

      cy.get(`[data-cy="fieldTemporalRelationToSleep"]`).type('23233').should('have.value', '23233');

      cy.get(`[data-cy="fieldBloodGlucoseSpecimenSource"]`).type('35607').should('have.value', '35607');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T11:55').blur().should('have.value', '2023-03-24T11:55');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        bloodGlucose = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', bloodGlucosePageUrlPattern);
    });
  });
});

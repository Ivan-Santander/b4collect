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

describe('SleepScores e2e test', () => {
  const sleepScoresPageUrl = '/sleep-scores';
  const sleepScoresPageUrlPattern = new RegExp('/sleep-scores(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sleepScoresSample = {};

  let sleepScores;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sleep-scores+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sleep-scores').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sleep-scores/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (sleepScores) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sleep-scores/${sleepScores.id}`,
      }).then(() => {
        sleepScores = undefined;
      });
    }
  });

  it('SleepScores menu should load SleepScores page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('sleep-scores');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SleepScores').should('exist');
    cy.url().should('match', sleepScoresPageUrlPattern);
  });

  describe('SleepScores page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sleepScoresPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SleepScores page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/sleep-scores/new$'));
        cy.getEntityCreateUpdateHeading('SleepScores');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepScoresPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/sleep-scores',
          body: sleepScoresSample,
        }).then(({ body }) => {
          sleepScores = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sleep-scores+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/sleep-scores?page=0&size=20>; rel="last",<http://localhost/api/sleep-scores?page=0&size=20>; rel="first"',
              },
              body: [sleepScores],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(sleepScoresPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SleepScores page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sleepScores');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepScoresPageUrlPattern);
      });

      it('edit button click should load edit SleepScores page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SleepScores');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepScoresPageUrlPattern);
      });

      it('edit button click should load edit SleepScores page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SleepScores');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepScoresPageUrlPattern);
      });

      it('last delete button click should delete instance of SleepScores', () => {
        cy.intercept('GET', '/api/sleep-scores/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('sleepScores').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepScoresPageUrlPattern);

        sleepScores = undefined;
      });
    });
  });

  describe('new SleepScores page', () => {
    beforeEach(() => {
      cy.visit(`${sleepScoresPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SleepScores');
    });

    it('should create an instance of SleepScores', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Guapo cross-media input').should('have.value', 'Guapo cross-media input');

      cy.get(`[data-cy="empresaId"]`).type('Extremadura Acero').should('have.value', 'Extremadura Acero');

      cy.get(`[data-cy="sleepQualityRatingScore"]`).type('31224').should('have.value', '31224');

      cy.get(`[data-cy="sleepEfficiencyScore"]`).type('41943').should('have.value', '41943');

      cy.get(`[data-cy="sleepGooalSecondsScore"]`).type('36397').should('have.value', '36397');

      cy.get(`[data-cy="sleepContinuityScore"]`).type('66778').should('have.value', '66778');

      cy.get(`[data-cy="sleepContinuityRating"]`).type('9618').should('have.value', '9618');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        sleepScores = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', sleepScoresPageUrlPattern);
    });
  });
});

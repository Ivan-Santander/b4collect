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

describe('ActivitySummary e2e test', () => {
  const activitySummaryPageUrl = '/activity-summary';
  const activitySummaryPageUrlPattern = new RegExp('/activity-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const activitySummarySample = {};

  let activitySummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/activity-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/activity-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/activity-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (activitySummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/activity-summaries/${activitySummary.id}`,
      }).then(() => {
        activitySummary = undefined;
      });
    }
  });

  it('ActivitySummaries menu should load ActivitySummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('activity-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ActivitySummary').should('exist');
    cy.url().should('match', activitySummaryPageUrlPattern);
  });

  describe('ActivitySummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(activitySummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ActivitySummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/activity-summary/new$'));
        cy.getEntityCreateUpdateHeading('ActivitySummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activitySummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/activity-summaries',
          body: activitySummarySample,
        }).then(({ body }) => {
          activitySummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/activity-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/activity-summaries?page=0&size=20>; rel="last",<http://localhost/api/activity-summaries?page=0&size=20>; rel="first"',
              },
              body: [activitySummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(activitySummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ActivitySummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('activitySummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activitySummaryPageUrlPattern);
      });

      it('edit button click should load edit ActivitySummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ActivitySummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activitySummaryPageUrlPattern);
      });

      it('edit button click should load edit ActivitySummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ActivitySummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activitySummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of ActivitySummary', () => {
        cy.intercept('GET', '/api/activity-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('activitySummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activitySummaryPageUrlPattern);

        activitySummary = undefined;
      });
    });
  });

  describe('new ActivitySummary page', () => {
    beforeEach(() => {
      cy.visit(`${activitySummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ActivitySummary');
    });

    it('should create an instance of ActivitySummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('synergize Hormigon').should('have.value', 'synergize Hormigon');

      cy.get(`[data-cy="empresaId"]`).type('Bedfordshire e-business').should('have.value', 'Bedfordshire e-business');

      cy.get(`[data-cy="fieldActivity"]`).type('4932').should('have.value', '4932');

      cy.get(`[data-cy="fieldDuration"]`).type('84577').should('have.value', '84577');

      cy.get(`[data-cy="fieldNumSegments"]`).type('68242').should('have.value', '68242');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T22:38').blur().should('have.value', '2023-03-23T22:38');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T13:36').blur().should('have.value', '2023-03-24T13:36');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        activitySummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', activitySummaryPageUrlPattern);
    });
  });
});

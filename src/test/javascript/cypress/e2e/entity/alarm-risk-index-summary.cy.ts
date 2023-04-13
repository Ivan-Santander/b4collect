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

describe('AlarmRiskIndexSummary e2e test', () => {
  const alarmRiskIndexSummaryPageUrl = '/alarm-risk-index-summary';
  const alarmRiskIndexSummaryPageUrlPattern = new RegExp('/alarm-risk-index-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const alarmRiskIndexSummarySample = {};

  let alarmRiskIndexSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/alarm-risk-index-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/alarm-risk-index-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/alarm-risk-index-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (alarmRiskIndexSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/alarm-risk-index-summaries/${alarmRiskIndexSummary.id}`,
      }).then(() => {
        alarmRiskIndexSummary = undefined;
      });
    }
  });

  it('AlarmRiskIndexSummaries menu should load AlarmRiskIndexSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('alarm-risk-index-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('AlarmRiskIndexSummary').should('exist');
    cy.url().should('match', alarmRiskIndexSummaryPageUrlPattern);
  });

  describe('AlarmRiskIndexSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(alarmRiskIndexSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create AlarmRiskIndexSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/alarm-risk-index-summary/new$'));
        cy.getEntityCreateUpdateHeading('AlarmRiskIndexSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alarmRiskIndexSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/alarm-risk-index-summaries',
          body: alarmRiskIndexSummarySample,
        }).then(({ body }) => {
          alarmRiskIndexSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/alarm-risk-index-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/alarm-risk-index-summaries?page=0&size=20>; rel="last",<http://localhost/api/alarm-risk-index-summaries?page=0&size=20>; rel="first"',
              },
              body: [alarmRiskIndexSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(alarmRiskIndexSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details AlarmRiskIndexSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('alarmRiskIndexSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alarmRiskIndexSummaryPageUrlPattern);
      });

      it('edit button click should load edit AlarmRiskIndexSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AlarmRiskIndexSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alarmRiskIndexSummaryPageUrlPattern);
      });

      it('edit button click should load edit AlarmRiskIndexSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('AlarmRiskIndexSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alarmRiskIndexSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of AlarmRiskIndexSummary', () => {
        cy.intercept('GET', '/api/alarm-risk-index-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('alarmRiskIndexSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', alarmRiskIndexSummaryPageUrlPattern);

        alarmRiskIndexSummary = undefined;
      });
    });
  });

  describe('new AlarmRiskIndexSummary page', () => {
    beforeEach(() => {
      cy.visit(`${alarmRiskIndexSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('AlarmRiskIndexSummary');
    });

    it('should create an instance of AlarmRiskIndexSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Burundi Métricas Ouguiya').should('have.value', 'Burundi Métricas Ouguiya');

      cy.get(`[data-cy="empresaId"]`).type('Adelante').should('have.value', 'Adelante');

      cy.get(`[data-cy="fieldAlarmRiskAverage"]`).type('21722').should('have.value', '21722');

      cy.get(`[data-cy="fieldAlarmRiskMax"]`).type('5413').should('have.value', '5413');

      cy.get(`[data-cy="fieldAlarmRiskMin"]`).type('92728').should('have.value', '92728');

      cy.get(`[data-cy="startTime"]`).type('2023-04-09T16:33').blur().should('have.value', '2023-04-09T16:33');

      cy.get(`[data-cy="endTime"]`).type('2023-04-10T01:09').blur().should('have.value', '2023-04-10T01:09');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        alarmRiskIndexSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', alarmRiskIndexSummaryPageUrlPattern);
    });
  });
});

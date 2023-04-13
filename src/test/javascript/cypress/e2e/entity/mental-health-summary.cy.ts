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

describe('MentalHealthSummary e2e test', () => {
  const mentalHealthSummaryPageUrl = '/mental-health-summary';
  const mentalHealthSummaryPageUrlPattern = new RegExp('/mental-health-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const mentalHealthSummarySample = {};

  let mentalHealthSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/mental-health-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/mental-health-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/mental-health-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (mentalHealthSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/mental-health-summaries/${mentalHealthSummary.id}`,
      }).then(() => {
        mentalHealthSummary = undefined;
      });
    }
  });

  it('MentalHealthSummaries menu should load MentalHealthSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('mental-health-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MentalHealthSummary').should('exist');
    cy.url().should('match', mentalHealthSummaryPageUrlPattern);
  });

  describe('MentalHealthSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(mentalHealthSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MentalHealthSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/mental-health-summary/new$'));
        cy.getEntityCreateUpdateHeading('MentalHealthSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/mental-health-summaries',
          body: mentalHealthSummarySample,
        }).then(({ body }) => {
          mentalHealthSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/mental-health-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/mental-health-summaries?page=0&size=20>; rel="last",<http://localhost/api/mental-health-summaries?page=0&size=20>; rel="first"',
              },
              body: [mentalHealthSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(mentalHealthSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MentalHealthSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('mentalHealthSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthSummaryPageUrlPattern);
      });

      it('edit button click should load edit MentalHealthSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MentalHealthSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthSummaryPageUrlPattern);
      });

      it('edit button click should load edit MentalHealthSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MentalHealthSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of MentalHealthSummary', () => {
        cy.intercept('GET', '/api/mental-health-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('mentalHealthSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', mentalHealthSummaryPageUrlPattern);

        mentalHealthSummary = undefined;
      });
    });
  });

  describe('new MentalHealthSummary page', () => {
    beforeEach(() => {
      cy.visit(`${mentalHealthSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MentalHealthSummary');
    });

    it('should create an instance of MentalHealthSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('haptic Inversor').should('have.value', 'haptic Inversor');

      cy.get(`[data-cy="empresaId"]`).type('Marruecos Avon actitud').should('have.value', 'Marruecos Avon actitud');

      cy.get(`[data-cy="emotionDescripMain"]`).type('Principe').should('have.value', 'Principe');

      cy.get(`[data-cy="emotionDescripSecond"]`).type('backing dot-com').should('have.value', 'backing dot-com');

      cy.get(`[data-cy="fieldMentalHealthAverage"]`).type('96571').should('have.value', '96571');

      cy.get(`[data-cy="fieldMentalHealthMax"]`).type('57463').should('have.value', '57463');

      cy.get(`[data-cy="fieldMentalHealthMin"]`).type('81759').should('have.value', '81759');

      cy.get(`[data-cy="scoreMentalRisk"]`).type('71676').should('have.value', '71676');

      cy.get(`[data-cy="startTime"]`).type('2023-04-10T10:44').blur().should('have.value', '2023-04-10T10:44');

      cy.get(`[data-cy="endTime"]`).type('2023-04-09T21:57').blur().should('have.value', '2023-04-09T21:57');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        mentalHealthSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', mentalHealthSummaryPageUrlPattern);
    });
  });
});

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

describe('BodyFatPercentageSummary e2e test', () => {
  const bodyFatPercentageSummaryPageUrl = '/body-fat-percentage-summary';
  const bodyFatPercentageSummaryPageUrlPattern = new RegExp('/body-fat-percentage-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bodyFatPercentageSummarySample = {};

  let bodyFatPercentageSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/body-fat-percentage-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/body-fat-percentage-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/body-fat-percentage-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bodyFatPercentageSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/body-fat-percentage-summaries/${bodyFatPercentageSummary.id}`,
      }).then(() => {
        bodyFatPercentageSummary = undefined;
      });
    }
  });

  it('BodyFatPercentageSummaries menu should load BodyFatPercentageSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('body-fat-percentage-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BodyFatPercentageSummary').should('exist');
    cy.url().should('match', bodyFatPercentageSummaryPageUrlPattern);
  });

  describe('BodyFatPercentageSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bodyFatPercentageSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BodyFatPercentageSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/body-fat-percentage-summary/new$'));
        cy.getEntityCreateUpdateHeading('BodyFatPercentageSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentageSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/body-fat-percentage-summaries',
          body: bodyFatPercentageSummarySample,
        }).then(({ body }) => {
          bodyFatPercentageSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/body-fat-percentage-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/body-fat-percentage-summaries?page=0&size=20>; rel="last",<http://localhost/api/body-fat-percentage-summaries?page=0&size=20>; rel="first"',
              },
              body: [bodyFatPercentageSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(bodyFatPercentageSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BodyFatPercentageSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bodyFatPercentageSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentageSummaryPageUrlPattern);
      });

      it('edit button click should load edit BodyFatPercentageSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BodyFatPercentageSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentageSummaryPageUrlPattern);
      });

      it('edit button click should load edit BodyFatPercentageSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BodyFatPercentageSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentageSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of BodyFatPercentageSummary', () => {
        cy.intercept('GET', '/api/body-fat-percentage-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('bodyFatPercentageSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', bodyFatPercentageSummaryPageUrlPattern);

        bodyFatPercentageSummary = undefined;
      });
    });
  });

  describe('new BodyFatPercentageSummary page', () => {
    beforeEach(() => {
      cy.visit(`${bodyFatPercentageSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BodyFatPercentageSummary');
    });

    it('should create an instance of BodyFatPercentageSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Informática facilitate payment').should('have.value', 'Informática facilitate payment');

      cy.get(`[data-cy="empresaId"]`).type('program Tonga').should('have.value', 'program Tonga');

      cy.get(`[data-cy="fieldAverage"]`).type('3924').should('have.value', '3924');

      cy.get(`[data-cy="fieldMax"]`).type('2241').should('have.value', '2241');

      cy.get(`[data-cy="fieldMin"]`).type('91735').should('have.value', '91735');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T22:22').blur().should('have.value', '2023-03-23T22:22');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T03:13').blur().should('have.value', '2023-03-24T03:13');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        bodyFatPercentageSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', bodyFatPercentageSummaryPageUrlPattern);
    });
  });
});

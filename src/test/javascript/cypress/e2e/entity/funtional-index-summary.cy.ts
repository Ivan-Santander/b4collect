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

describe('FuntionalIndexSummary e2e test', () => {
  const funtionalIndexSummaryPageUrl = '/funtional-index-summary';
  const funtionalIndexSummaryPageUrlPattern = new RegExp('/funtional-index-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const funtionalIndexSummarySample = {};

  let funtionalIndexSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/funtional-index-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/funtional-index-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/funtional-index-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (funtionalIndexSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/funtional-index-summaries/${funtionalIndexSummary.id}`,
      }).then(() => {
        funtionalIndexSummary = undefined;
      });
    }
  });

  it('FuntionalIndexSummaries menu should load FuntionalIndexSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('funtional-index-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FuntionalIndexSummary').should('exist');
    cy.url().should('match', funtionalIndexSummaryPageUrlPattern);
  });

  describe('FuntionalIndexSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(funtionalIndexSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FuntionalIndexSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/funtional-index-summary/new$'));
        cy.getEntityCreateUpdateHeading('FuntionalIndexSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/funtional-index-summaries',
          body: funtionalIndexSummarySample,
        }).then(({ body }) => {
          funtionalIndexSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/funtional-index-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/funtional-index-summaries?page=0&size=20>; rel="last",<http://localhost/api/funtional-index-summaries?page=0&size=20>; rel="first"',
              },
              body: [funtionalIndexSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(funtionalIndexSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FuntionalIndexSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('funtionalIndexSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexSummaryPageUrlPattern);
      });

      it('edit button click should load edit FuntionalIndexSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FuntionalIndexSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexSummaryPageUrlPattern);
      });

      it('edit button click should load edit FuntionalIndexSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FuntionalIndexSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of FuntionalIndexSummary', () => {
        cy.intercept('GET', '/api/funtional-index-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('funtionalIndexSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexSummaryPageUrlPattern);

        funtionalIndexSummary = undefined;
      });
    });
  });

  describe('new FuntionalIndexSummary page', () => {
    beforeEach(() => {
      cy.visit(`${funtionalIndexSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FuntionalIndexSummary');
    });

    it('should create an instance of FuntionalIndexSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('primary metrics Ejecutivo').should('have.value', 'primary metrics Ejecutivo');

      cy.get(`[data-cy="empresaId"]`).type('parse calculate').should('have.value', 'parse calculate');

      cy.get(`[data-cy="fieldFuntionalIndexAverage"]`).type('87484').should('have.value', '87484');

      cy.get(`[data-cy="fieldFuntionalIndexMax"]`).type('11616').should('have.value', '11616');

      cy.get(`[data-cy="fieldFuntionalIndexMin"]`).type('14495').should('have.value', '14495');

      cy.get(`[data-cy="startTime"]`).type('2023-04-10T04:31').blur().should('have.value', '2023-04-10T04:31');

      cy.get(`[data-cy="endTime"]`).type('2023-04-10T00:37').blur().should('have.value', '2023-04-10T00:37');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        funtionalIndexSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', funtionalIndexSummaryPageUrlPattern);
    });
  });
});

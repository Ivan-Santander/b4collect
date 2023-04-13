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

describe('OxygenSaturationSummary e2e test', () => {
  const oxygenSaturationSummaryPageUrl = '/oxygen-saturation-summary';
  const oxygenSaturationSummaryPageUrlPattern = new RegExp('/oxygen-saturation-summary(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const oxygenSaturationSummarySample = {};

  let oxygenSaturationSummary;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/oxygen-saturation-summaries+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/oxygen-saturation-summaries').as('postEntityRequest');
    cy.intercept('DELETE', '/api/oxygen-saturation-summaries/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (oxygenSaturationSummary) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/oxygen-saturation-summaries/${oxygenSaturationSummary.id}`,
      }).then(() => {
        oxygenSaturationSummary = undefined;
      });
    }
  });

  it('OxygenSaturationSummaries menu should load OxygenSaturationSummaries page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('oxygen-saturation-summary');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('OxygenSaturationSummary').should('exist');
    cy.url().should('match', oxygenSaturationSummaryPageUrlPattern);
  });

  describe('OxygenSaturationSummary page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(oxygenSaturationSummaryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create OxygenSaturationSummary page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/oxygen-saturation-summary/new$'));
        cy.getEntityCreateUpdateHeading('OxygenSaturationSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationSummaryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/oxygen-saturation-summaries',
          body: oxygenSaturationSummarySample,
        }).then(({ body }) => {
          oxygenSaturationSummary = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/oxygen-saturation-summaries+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/oxygen-saturation-summaries?page=0&size=20>; rel="last",<http://localhost/api/oxygen-saturation-summaries?page=0&size=20>; rel="first"',
              },
              body: [oxygenSaturationSummary],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(oxygenSaturationSummaryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details OxygenSaturationSummary page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('oxygenSaturationSummary');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationSummaryPageUrlPattern);
      });

      it('edit button click should load edit OxygenSaturationSummary page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('OxygenSaturationSummary');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationSummaryPageUrlPattern);
      });

      it('edit button click should load edit OxygenSaturationSummary page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('OxygenSaturationSummary');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationSummaryPageUrlPattern);
      });

      it('last delete button click should delete instance of OxygenSaturationSummary', () => {
        cy.intercept('GET', '/api/oxygen-saturation-summaries/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('oxygenSaturationSummary').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationSummaryPageUrlPattern);

        oxygenSaturationSummary = undefined;
      });
    });
  });

  describe('new OxygenSaturationSummary page', () => {
    beforeEach(() => {
      cy.visit(`${oxygenSaturationSummaryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('OxygenSaturationSummary');
    });

    it('should create an instance of OxygenSaturationSummary', () => {
      cy.get(`[data-cy="usuarioId"]`).type('bifurcada Madera').should('have.value', 'bifurcada Madera');

      cy.get(`[data-cy="empresaId"]`).type('target Guapa').should('have.value', 'target Guapa');

      cy.get(`[data-cy="fieldOxigenSaturationAverage"]`).type('53777').should('have.value', '53777');

      cy.get(`[data-cy="fieldOxigenSaturationMax"]`).type('90953').should('have.value', '90953');

      cy.get(`[data-cy="fieldOxigenSaturationMin"]`).type('66947').should('have.value', '66947');

      cy.get(`[data-cy="fieldSuplementalOxigenFlowRateAverage"]`).type('17430').should('have.value', '17430');

      cy.get(`[data-cy="fieldSuplementalOxigenFlowRateMax"]`).type('64378').should('have.value', '64378');

      cy.get(`[data-cy="fieldSuplementalOxigenFlowRateMin"]`).type('78913').should('have.value', '78913');

      cy.get(`[data-cy="fieldOxigenTherapyAdministrationMode"]`).type('41975').should('have.value', '41975');

      cy.get(`[data-cy="fieldOxigenSaturationMode"]`).type('45464').should('have.value', '45464');

      cy.get(`[data-cy="fieldOxigenSaturationMeasurementMethod"]`).type('94035').should('have.value', '94035');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T12:30').blur().should('have.value', '2023-03-24T12:30');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        oxygenSaturationSummary = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', oxygenSaturationSummaryPageUrlPattern);
    });
  });
});

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

describe('PoweSample e2e test', () => {
  const poweSamplePageUrl = '/powe-sample';
  const poweSamplePageUrlPattern = new RegExp('/powe-sample(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const poweSampleSample = {};

  let poweSample;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/powe-samples+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/powe-samples').as('postEntityRequest');
    cy.intercept('DELETE', '/api/powe-samples/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (poweSample) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/powe-samples/${poweSample.id}`,
      }).then(() => {
        poweSample = undefined;
      });
    }
  });

  it('PoweSamples menu should load PoweSamples page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('powe-sample');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PoweSample').should('exist');
    cy.url().should('match', poweSamplePageUrlPattern);
  });

  describe('PoweSample page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(poweSamplePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PoweSample page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/powe-sample/new$'));
        cy.getEntityCreateUpdateHeading('PoweSample');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', poweSamplePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/powe-samples',
          body: poweSampleSample,
        }).then(({ body }) => {
          poweSample = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/powe-samples+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/powe-samples?page=0&size=20>; rel="last",<http://localhost/api/powe-samples?page=0&size=20>; rel="first"',
              },
              body: [poweSample],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(poweSamplePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PoweSample page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('poweSample');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', poweSamplePageUrlPattern);
      });

      it('edit button click should load edit PoweSample page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PoweSample');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', poweSamplePageUrlPattern);
      });

      it('edit button click should load edit PoweSample page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PoweSample');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', poweSamplePageUrlPattern);
      });

      it('last delete button click should delete instance of PoweSample', () => {
        cy.intercept('GET', '/api/powe-samples/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('poweSample').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', poweSamplePageUrlPattern);

        poweSample = undefined;
      });
    });
  });

  describe('new PoweSample page', () => {
    beforeEach(() => {
      cy.visit(`${poweSamplePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PoweSample');
    });

    it('should create an instance of PoweSample', () => {
      cy.get(`[data-cy="usuarioId"]`).type('innovate Italia Madera').should('have.value', 'innovate Italia Madera');

      cy.get(`[data-cy="empresaId"]`).type('XML Bangladesh').should('have.value', 'XML Bangladesh');

      cy.get(`[data-cy="vatios"]`).type('51706').should('have.value', '51706');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T19:28').blur().should('have.value', '2023-03-23T19:28');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T10:20').blur().should('have.value', '2023-03-24T10:20');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        poweSample = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', poweSamplePageUrlPattern);
    });
  });
});

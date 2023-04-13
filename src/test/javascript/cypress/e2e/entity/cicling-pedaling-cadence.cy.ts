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

describe('CiclingPedalingCadence e2e test', () => {
  const ciclingPedalingCadencePageUrl = '/cicling-pedaling-cadence';
  const ciclingPedalingCadencePageUrlPattern = new RegExp('/cicling-pedaling-cadence(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const ciclingPedalingCadenceSample = {};

  let ciclingPedalingCadence;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/cicling-pedaling-cadences+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/cicling-pedaling-cadences').as('postEntityRequest');
    cy.intercept('DELETE', '/api/cicling-pedaling-cadences/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (ciclingPedalingCadence) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/cicling-pedaling-cadences/${ciclingPedalingCadence.id}`,
      }).then(() => {
        ciclingPedalingCadence = undefined;
      });
    }
  });

  it('CiclingPedalingCadences menu should load CiclingPedalingCadences page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cicling-pedaling-cadence');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CiclingPedalingCadence').should('exist');
    cy.url().should('match', ciclingPedalingCadencePageUrlPattern);
  });

  describe('CiclingPedalingCadence page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(ciclingPedalingCadencePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CiclingPedalingCadence page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cicling-pedaling-cadence/new$'));
        cy.getEntityCreateUpdateHeading('CiclingPedalingCadence');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ciclingPedalingCadencePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/cicling-pedaling-cadences',
          body: ciclingPedalingCadenceSample,
        }).then(({ body }) => {
          ciclingPedalingCadence = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/cicling-pedaling-cadences+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/cicling-pedaling-cadences?page=0&size=20>; rel="last",<http://localhost/api/cicling-pedaling-cadences?page=0&size=20>; rel="first"',
              },
              body: [ciclingPedalingCadence],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(ciclingPedalingCadencePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CiclingPedalingCadence page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('ciclingPedalingCadence');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ciclingPedalingCadencePageUrlPattern);
      });

      it('edit button click should load edit CiclingPedalingCadence page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CiclingPedalingCadence');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ciclingPedalingCadencePageUrlPattern);
      });

      it('edit button click should load edit CiclingPedalingCadence page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CiclingPedalingCadence');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ciclingPedalingCadencePageUrlPattern);
      });

      it('last delete button click should delete instance of CiclingPedalingCadence', () => {
        cy.intercept('GET', '/api/cicling-pedaling-cadences/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('ciclingPedalingCadence').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', ciclingPedalingCadencePageUrlPattern);

        ciclingPedalingCadence = undefined;
      });
    });
  });

  describe('new CiclingPedalingCadence page', () => {
    beforeEach(() => {
      cy.visit(`${ciclingPedalingCadencePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CiclingPedalingCadence');
    });

    it('should create an instance of CiclingPedalingCadence', () => {
      cy.get(`[data-cy="usuarioId"]`).type('calculate Ordenador').should('have.value', 'calculate Ordenador');

      cy.get(`[data-cy="empresaId"]`).type('bypassing').should('have.value', 'bypassing');

      cy.get(`[data-cy="rpm"]`).type('72747').should('have.value', '72747');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T23:56').blur().should('have.value', '2023-03-23T23:56');

      cy.get(`[data-cy="endTime"]`).type('2023-03-23T19:23').blur().should('have.value', '2023-03-23T19:23');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        ciclingPedalingCadence = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', ciclingPedalingCadencePageUrlPattern);
    });
  });
});

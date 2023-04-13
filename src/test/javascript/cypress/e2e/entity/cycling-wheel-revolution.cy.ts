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

describe('CyclingWheelRevolution e2e test', () => {
  const cyclingWheelRevolutionPageUrl = '/cycling-wheel-revolution';
  const cyclingWheelRevolutionPageUrlPattern = new RegExp('/cycling-wheel-revolution(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const cyclingWheelRevolutionSample = {};

  let cyclingWheelRevolution;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/cycling-wheel-revolutions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/cycling-wheel-revolutions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/cycling-wheel-revolutions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cyclingWheelRevolution) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/cycling-wheel-revolutions/${cyclingWheelRevolution.id}`,
      }).then(() => {
        cyclingWheelRevolution = undefined;
      });
    }
  });

  it('CyclingWheelRevolutions menu should load CyclingWheelRevolutions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cycling-wheel-revolution');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CyclingWheelRevolution').should('exist');
    cy.url().should('match', cyclingWheelRevolutionPageUrlPattern);
  });

  describe('CyclingWheelRevolution page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cyclingWheelRevolutionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CyclingWheelRevolution page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cycling-wheel-revolution/new$'));
        cy.getEntityCreateUpdateHeading('CyclingWheelRevolution');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cyclingWheelRevolutionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/cycling-wheel-revolutions',
          body: cyclingWheelRevolutionSample,
        }).then(({ body }) => {
          cyclingWheelRevolution = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/cycling-wheel-revolutions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/cycling-wheel-revolutions?page=0&size=20>; rel="last",<http://localhost/api/cycling-wheel-revolutions?page=0&size=20>; rel="first"',
              },
              body: [cyclingWheelRevolution],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(cyclingWheelRevolutionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CyclingWheelRevolution page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cyclingWheelRevolution');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cyclingWheelRevolutionPageUrlPattern);
      });

      it('edit button click should load edit CyclingWheelRevolution page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CyclingWheelRevolution');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cyclingWheelRevolutionPageUrlPattern);
      });

      it('edit button click should load edit CyclingWheelRevolution page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CyclingWheelRevolution');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cyclingWheelRevolutionPageUrlPattern);
      });

      it('last delete button click should delete instance of CyclingWheelRevolution', () => {
        cy.intercept('GET', '/api/cycling-wheel-revolutions/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('cyclingWheelRevolution').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', cyclingWheelRevolutionPageUrlPattern);

        cyclingWheelRevolution = undefined;
      });
    });
  });

  describe('new CyclingWheelRevolution page', () => {
    beforeEach(() => {
      cy.visit(`${cyclingWheelRevolutionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CyclingWheelRevolution');
    });

    it('should create an instance of CyclingWheelRevolution', () => {
      cy.get(`[data-cy="usuarioId"]`).type('invoice Cliente primary').should('have.value', 'invoice Cliente primary');

      cy.get(`[data-cy="empresaId"]`).type('Heredado open-source').should('have.value', 'Heredado open-source');

      cy.get(`[data-cy="revolutions"]`).type('43994').should('have.value', '43994');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T11:35').blur().should('have.value', '2023-03-24T11:35');

      cy.get(`[data-cy="endTime"]`).type('2023-03-23T15:34').blur().should('have.value', '2023-03-23T15:34');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        cyclingWheelRevolution = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', cyclingWheelRevolutionPageUrlPattern);
    });
  });
});

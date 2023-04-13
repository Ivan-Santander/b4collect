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

describe('DistanceDelta e2e test', () => {
  const distanceDeltaPageUrl = '/distance-delta';
  const distanceDeltaPageUrlPattern = new RegExp('/distance-delta(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const distanceDeltaSample = {};

  let distanceDelta;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/distance-deltas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/distance-deltas').as('postEntityRequest');
    cy.intercept('DELETE', '/api/distance-deltas/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (distanceDelta) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/distance-deltas/${distanceDelta.id}`,
      }).then(() => {
        distanceDelta = undefined;
      });
    }
  });

  it('DistanceDeltas menu should load DistanceDeltas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('distance-delta');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DistanceDelta').should('exist');
    cy.url().should('match', distanceDeltaPageUrlPattern);
  });

  describe('DistanceDelta page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(distanceDeltaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DistanceDelta page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/distance-delta/new$'));
        cy.getEntityCreateUpdateHeading('DistanceDelta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', distanceDeltaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/distance-deltas',
          body: distanceDeltaSample,
        }).then(({ body }) => {
          distanceDelta = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/distance-deltas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/distance-deltas?page=0&size=20>; rel="last",<http://localhost/api/distance-deltas?page=0&size=20>; rel="first"',
              },
              body: [distanceDelta],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(distanceDeltaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DistanceDelta page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('distanceDelta');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', distanceDeltaPageUrlPattern);
      });

      it('edit button click should load edit DistanceDelta page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DistanceDelta');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', distanceDeltaPageUrlPattern);
      });

      it('edit button click should load edit DistanceDelta page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DistanceDelta');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', distanceDeltaPageUrlPattern);
      });

      it('last delete button click should delete instance of DistanceDelta', () => {
        cy.intercept('GET', '/api/distance-deltas/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('distanceDelta').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', distanceDeltaPageUrlPattern);

        distanceDelta = undefined;
      });
    });
  });

  describe('new DistanceDelta page', () => {
    beforeEach(() => {
      cy.visit(`${distanceDeltaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DistanceDelta');
    });

    it('should create an instance of DistanceDelta', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Negro').should('have.value', 'Negro');

      cy.get(`[data-cy="empresaId"]`).type('Negro Hormigon Algodón').should('have.value', 'Negro Hormigon Algodón');

      cy.get(`[data-cy="distance"]`).type('13804').should('have.value', '13804');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T09:44').blur().should('have.value', '2023-03-24T09:44');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T12:30').blur().should('have.value', '2023-03-24T12:30');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        distanceDelta = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', distanceDeltaPageUrlPattern);
    });
  });
});

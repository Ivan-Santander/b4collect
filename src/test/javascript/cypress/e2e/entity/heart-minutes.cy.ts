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

describe('HeartMinutes e2e test', () => {
  const heartMinutesPageUrl = '/heart-minutes';
  const heartMinutesPageUrlPattern = new RegExp('/heart-minutes(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const heartMinutesSample = {};

  let heartMinutes;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/heart-minutes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/heart-minutes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/heart-minutes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (heartMinutes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/heart-minutes/${heartMinutes.id}`,
      }).then(() => {
        heartMinutes = undefined;
      });
    }
  });

  it('HeartMinutes menu should load HeartMinutes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('heart-minutes');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('HeartMinutes').should('exist');
    cy.url().should('match', heartMinutesPageUrlPattern);
  });

  describe('HeartMinutes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(heartMinutesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create HeartMinutes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/heart-minutes/new$'));
        cy.getEntityCreateUpdateHeading('HeartMinutes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartMinutesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/heart-minutes',
          body: heartMinutesSample,
        }).then(({ body }) => {
          heartMinutes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/heart-minutes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/heart-minutes?page=0&size=20>; rel="last",<http://localhost/api/heart-minutes?page=0&size=20>; rel="first"',
              },
              body: [heartMinutes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(heartMinutesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details HeartMinutes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('heartMinutes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartMinutesPageUrlPattern);
      });

      it('edit button click should load edit HeartMinutes page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HeartMinutes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartMinutesPageUrlPattern);
      });

      it('edit button click should load edit HeartMinutes page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HeartMinutes');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartMinutesPageUrlPattern);
      });

      it('last delete button click should delete instance of HeartMinutes', () => {
        cy.intercept('GET', '/api/heart-minutes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('heartMinutes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartMinutesPageUrlPattern);

        heartMinutes = undefined;
      });
    });
  });

  describe('new HeartMinutes page', () => {
    beforeEach(() => {
      cy.visit(`${heartMinutesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('HeartMinutes');
    });

    it('should create an instance of HeartMinutes', () => {
      cy.get(`[data-cy="usuarioId"]`).type('calculating Productor').should('have.value', 'calculating Productor');

      cy.get(`[data-cy="empresaId"]`).type('parsing').should('have.value', 'parsing');

      cy.get(`[data-cy="severity"]`).type('21996').should('have.value', '21996');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T09:07').blur().should('have.value', '2023-03-24T09:07');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T07:02').blur().should('have.value', '2023-03-24T07:02');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        heartMinutes = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', heartMinutesPageUrlPattern);
    });
  });
});

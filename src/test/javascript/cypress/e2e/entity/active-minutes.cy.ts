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

describe('ActiveMinutes e2e test', () => {
  const activeMinutesPageUrl = '/active-minutes';
  const activeMinutesPageUrlPattern = new RegExp('/active-minutes(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const activeMinutesSample = {};

  let activeMinutes;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/active-minutes+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/active-minutes').as('postEntityRequest');
    cy.intercept('DELETE', '/api/active-minutes/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (activeMinutes) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/active-minutes/${activeMinutes.id}`,
      }).then(() => {
        activeMinutes = undefined;
      });
    }
  });

  it('ActiveMinutes menu should load ActiveMinutes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('active-minutes');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ActiveMinutes').should('exist');
    cy.url().should('match', activeMinutesPageUrlPattern);
  });

  describe('ActiveMinutes page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(activeMinutesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ActiveMinutes page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/active-minutes/new$'));
        cy.getEntityCreateUpdateHeading('ActiveMinutes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activeMinutesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/active-minutes',
          body: activeMinutesSample,
        }).then(({ body }) => {
          activeMinutes = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/active-minutes+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/active-minutes?page=0&size=20>; rel="last",<http://localhost/api/active-minutes?page=0&size=20>; rel="first"',
              },
              body: [activeMinutes],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(activeMinutesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ActiveMinutes page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('activeMinutes');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activeMinutesPageUrlPattern);
      });

      it('edit button click should load edit ActiveMinutes page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ActiveMinutes');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activeMinutesPageUrlPattern);
      });

      it('edit button click should load edit ActiveMinutes page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ActiveMinutes');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activeMinutesPageUrlPattern);
      });

      it('last delete button click should delete instance of ActiveMinutes', () => {
        cy.intercept('GET', '/api/active-minutes/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('activeMinutes').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', activeMinutesPageUrlPattern);

        activeMinutes = undefined;
      });
    });
  });

  describe('new ActiveMinutes page', () => {
    beforeEach(() => {
      cy.visit(`${activeMinutesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ActiveMinutes');
    });

    it('should create an instance of ActiveMinutes', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Ferrocarril').should('have.value', 'Ferrocarril');

      cy.get(`[data-cy="empresaId"]`).type('Normas Algodón').should('have.value', 'Normas Algodón');

      cy.get(`[data-cy="calorias"]`).type('99238').should('have.value', '99238');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T05:02').blur().should('have.value', '2023-03-24T05:02');

      cy.get(`[data-cy="endTime"]`).type('2023-03-23T19:39').blur().should('have.value', '2023-03-23T19:39');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        activeMinutes = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', activeMinutesPageUrlPattern);
    });
  });
});

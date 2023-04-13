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

describe('HeartRateBpm e2e test', () => {
  const heartRateBpmPageUrl = '/heart-rate-bpm';
  const heartRateBpmPageUrlPattern = new RegExp('/heart-rate-bpm(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const heartRateBpmSample = {};

  let heartRateBpm;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/heart-rate-bpms+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/heart-rate-bpms').as('postEntityRequest');
    cy.intercept('DELETE', '/api/heart-rate-bpms/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (heartRateBpm) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/heart-rate-bpms/${heartRateBpm.id}`,
      }).then(() => {
        heartRateBpm = undefined;
      });
    }
  });

  it('HeartRateBpms menu should load HeartRateBpms page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('heart-rate-bpm');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('HeartRateBpm').should('exist');
    cy.url().should('match', heartRateBpmPageUrlPattern);
  });

  describe('HeartRateBpm page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(heartRateBpmPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create HeartRateBpm page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/heart-rate-bpm/new$'));
        cy.getEntityCreateUpdateHeading('HeartRateBpm');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateBpmPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/heart-rate-bpms',
          body: heartRateBpmSample,
        }).then(({ body }) => {
          heartRateBpm = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/heart-rate-bpms+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/heart-rate-bpms?page=0&size=20>; rel="last",<http://localhost/api/heart-rate-bpms?page=0&size=20>; rel="first"',
              },
              body: [heartRateBpm],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(heartRateBpmPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details HeartRateBpm page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('heartRateBpm');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateBpmPageUrlPattern);
      });

      it('edit button click should load edit HeartRateBpm page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HeartRateBpm');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateBpmPageUrlPattern);
      });

      it('edit button click should load edit HeartRateBpm page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HeartRateBpm');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateBpmPageUrlPattern);
      });

      it('last delete button click should delete instance of HeartRateBpm', () => {
        cy.intercept('GET', '/api/heart-rate-bpms/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('heartRateBpm').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', heartRateBpmPageUrlPattern);

        heartRateBpm = undefined;
      });
    });
  });

  describe('new HeartRateBpm page', () => {
    beforeEach(() => {
      cy.visit(`${heartRateBpmPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('HeartRateBpm');
    });

    it('should create an instance of HeartRateBpm', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Comunidad Sum').should('have.value', 'Comunidad Sum');

      cy.get(`[data-cy="empresaId"]`).type('streamline').should('have.value', 'streamline');

      cy.get(`[data-cy="ppm"]`).type('68445').should('have.value', '68445');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T07:23').blur().should('have.value', '2023-03-24T07:23');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        heartRateBpm = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', heartRateBpmPageUrlPattern);
    });
  });
});

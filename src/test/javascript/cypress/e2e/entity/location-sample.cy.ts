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

describe('LocationSample e2e test', () => {
  const locationSamplePageUrl = '/location-sample';
  const locationSamplePageUrlPattern = new RegExp('/location-sample(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const locationSampleSample = {};

  let locationSample;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/location-samples+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/location-samples').as('postEntityRequest');
    cy.intercept('DELETE', '/api/location-samples/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (locationSample) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/location-samples/${locationSample.id}`,
      }).then(() => {
        locationSample = undefined;
      });
    }
  });

  it('LocationSamples menu should load LocationSamples page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('location-sample');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('LocationSample').should('exist');
    cy.url().should('match', locationSamplePageUrlPattern);
  });

  describe('LocationSample page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(locationSamplePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create LocationSample page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/location-sample/new$'));
        cy.getEntityCreateUpdateHeading('LocationSample');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', locationSamplePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/location-samples',
          body: locationSampleSample,
        }).then(({ body }) => {
          locationSample = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/location-samples+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/location-samples?page=0&size=20>; rel="last",<http://localhost/api/location-samples?page=0&size=20>; rel="first"',
              },
              body: [locationSample],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(locationSamplePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details LocationSample page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('locationSample');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', locationSamplePageUrlPattern);
      });

      it('edit button click should load edit LocationSample page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LocationSample');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', locationSamplePageUrlPattern);
      });

      it('edit button click should load edit LocationSample page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('LocationSample');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', locationSamplePageUrlPattern);
      });

      it('last delete button click should delete instance of LocationSample', () => {
        cy.intercept('GET', '/api/location-samples/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('locationSample').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', locationSamplePageUrlPattern);

        locationSample = undefined;
      });
    });
  });

  describe('new LocationSample page', () => {
    beforeEach(() => {
      cy.visit(`${locationSamplePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('LocationSample');
    });

    it('should create an instance of LocationSample', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Director bandwidth').should('have.value', 'Director bandwidth');

      cy.get(`[data-cy="empresaId"]`).type('Pequeño').should('have.value', 'Pequeño');

      cy.get(`[data-cy="latitudMin"]`).type('39063').should('have.value', '39063');

      cy.get(`[data-cy="longitudMin"]`).type('92798').should('have.value', '92798');

      cy.get(`[data-cy="latitudMax"]`).type('90655').should('have.value', '90655');

      cy.get(`[data-cy="longitudMax"]`).type('67440').should('have.value', '67440');

      cy.get(`[data-cy="accuracy"]`).type('52308').should('have.value', '52308');

      cy.get(`[data-cy="altitud"]`).type('6243').should('have.value', '6243');

      cy.get(`[data-cy="startTime"]`).type('2023-03-24T00:41').blur().should('have.value', '2023-03-24T00:41');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T07:15').blur().should('have.value', '2023-03-24T07:15');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        locationSample = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', locationSamplePageUrlPattern);
    });
  });
});

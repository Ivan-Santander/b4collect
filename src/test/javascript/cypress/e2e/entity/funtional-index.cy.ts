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

describe('FuntionalIndex e2e test', () => {
  const funtionalIndexPageUrl = '/funtional-index';
  const funtionalIndexPageUrlPattern = new RegExp('/funtional-index(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const funtionalIndexSample = {};

  let funtionalIndex;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/funtional-indices+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/funtional-indices').as('postEntityRequest');
    cy.intercept('DELETE', '/api/funtional-indices/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (funtionalIndex) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/funtional-indices/${funtionalIndex.id}`,
      }).then(() => {
        funtionalIndex = undefined;
      });
    }
  });

  it('FuntionalIndices menu should load FuntionalIndices page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('funtional-index');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FuntionalIndex').should('exist');
    cy.url().should('match', funtionalIndexPageUrlPattern);
  });

  describe('FuntionalIndex page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(funtionalIndexPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FuntionalIndex page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/funtional-index/new$'));
        cy.getEntityCreateUpdateHeading('FuntionalIndex');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/funtional-indices',
          body: funtionalIndexSample,
        }).then(({ body }) => {
          funtionalIndex = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/funtional-indices+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/funtional-indices?page=0&size=20>; rel="last",<http://localhost/api/funtional-indices?page=0&size=20>; rel="first"',
              },
              body: [funtionalIndex],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(funtionalIndexPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FuntionalIndex page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('funtionalIndex');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexPageUrlPattern);
      });

      it('edit button click should load edit FuntionalIndex page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FuntionalIndex');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexPageUrlPattern);
      });

      it('edit button click should load edit FuntionalIndex page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FuntionalIndex');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexPageUrlPattern);
      });

      it('last delete button click should delete instance of FuntionalIndex', () => {
        cy.intercept('GET', '/api/funtional-indices/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('funtionalIndex').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', funtionalIndexPageUrlPattern);

        funtionalIndex = undefined;
      });
    });
  });

  describe('new FuntionalIndex page', () => {
    beforeEach(() => {
      cy.visit(`${funtionalIndexPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FuntionalIndex');
    });

    it('should create an instance of FuntionalIndex', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Buckinghamshire Castilla haptic').should('have.value', 'Buckinghamshire Castilla haptic');

      cy.get(`[data-cy="empresaId"]`).type('SMS Cuentas Optimización').should('have.value', 'SMS Cuentas Optimización');

      cy.get(`[data-cy="bodyHealthScore"]`).type('12878').should('have.value', '12878');

      cy.get(`[data-cy="mentalHealthScore"]`).type('759').should('have.value', '759');

      cy.get(`[data-cy="sleepHealthScore"]`).type('14647').should('have.value', '14647');

      cy.get(`[data-cy="funtionalIndex"]`).type('58929').should('have.value', '58929');

      cy.get(`[data-cy="alarmRiskScore"]`).type('91796').should('have.value', '91796');

      cy.get(`[data-cy="startTime"]`).type('2023-04-10T11:35').blur().should('have.value', '2023-04-10T11:35');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        funtionalIndex = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', funtionalIndexPageUrlPattern);
    });
  });
});

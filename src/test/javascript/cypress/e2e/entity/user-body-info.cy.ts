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

describe('UserBodyInfo e2e test', () => {
  const userBodyInfoPageUrl = '/user-body-info';
  const userBodyInfoPageUrlPattern = new RegExp('/user-body-info(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const userBodyInfoSample = {};

  let userBodyInfo;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/user-body-infos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/user-body-infos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/user-body-infos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (userBodyInfo) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-body-infos/${userBodyInfo.id}`,
      }).then(() => {
        userBodyInfo = undefined;
      });
    }
  });

  it('UserBodyInfos menu should load UserBodyInfos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('user-body-info');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UserBodyInfo').should('exist');
    cy.url().should('match', userBodyInfoPageUrlPattern);
  });

  describe('UserBodyInfo page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(userBodyInfoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UserBodyInfo page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/user-body-info/new$'));
        cy.getEntityCreateUpdateHeading('UserBodyInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userBodyInfoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/user-body-infos',
          body: userBodyInfoSample,
        }).then(({ body }) => {
          userBodyInfo = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/user-body-infos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/user-body-infos?page=0&size=20>; rel="last",<http://localhost/api/user-body-infos?page=0&size=20>; rel="first"',
              },
              body: [userBodyInfo],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(userBodyInfoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UserBodyInfo page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('userBodyInfo');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userBodyInfoPageUrlPattern);
      });

      it('edit button click should load edit UserBodyInfo page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserBodyInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userBodyInfoPageUrlPattern);
      });

      it('edit button click should load edit UserBodyInfo page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserBodyInfo');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userBodyInfoPageUrlPattern);
      });

      it('last delete button click should delete instance of UserBodyInfo', () => {
        cy.intercept('GET', '/api/user-body-infos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('userBodyInfo').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userBodyInfoPageUrlPattern);

        userBodyInfo = undefined;
      });
    });
  });

  describe('new UserBodyInfo page', () => {
    beforeEach(() => {
      cy.visit(`${userBodyInfoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UserBodyInfo');
    });

    it('should create an instance of UserBodyInfo', () => {
      cy.get(`[data-cy="usuarioId"]`).type('granular Regional open-source').should('have.value', 'granular Regional open-source');

      cy.get(`[data-cy="empresaId"]`).type('online').should('have.value', 'online');

      cy.get(`[data-cy="waistCircumference"]`).type('64709').should('have.value', '64709');

      cy.get(`[data-cy="hipCircumference"]`).type('95125').should('have.value', '95125');

      cy.get(`[data-cy="chestCircumference"]`).type('66753').should('have.value', '66753');

      cy.get(`[data-cy="boneCompositionPercentaje"]`).type('66540').should('have.value', '66540');

      cy.get(`[data-cy="muscleCompositionPercentaje"]`).type('7447').should('have.value', '7447');

      cy.get(`[data-cy="smoker"]`).should('not.be.checked');
      cy.get(`[data-cy="smoker"]`).click().should('be.checked');

      cy.get(`[data-cy="waightKg"]`).type('89949').should('have.value', '89949');

      cy.get(`[data-cy="heightCm"]`).type('91290').should('have.value', '91290');

      cy.get(`[data-cy="bodyHealthScore"]`).type('23785').should('have.value', '23785');

      cy.get(`[data-cy="cardiovascularRisk"]`).type('50653').should('have.value', '50653');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        userBodyInfo = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', userBodyInfoPageUrlPattern);
    });
  });
});

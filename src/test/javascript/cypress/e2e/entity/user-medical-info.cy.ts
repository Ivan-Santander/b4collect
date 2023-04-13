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

describe('UserMedicalInfo e2e test', () => {
  const userMedicalInfoPageUrl = '/user-medical-info';
  const userMedicalInfoPageUrlPattern = new RegExp('/user-medical-info(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const userMedicalInfoSample = {};

  let userMedicalInfo;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/user-medical-infos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/user-medical-infos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/user-medical-infos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (userMedicalInfo) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-medical-infos/${userMedicalInfo.id}`,
      }).then(() => {
        userMedicalInfo = undefined;
      });
    }
  });

  it('UserMedicalInfos menu should load UserMedicalInfos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('user-medical-info');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UserMedicalInfo').should('exist');
    cy.url().should('match', userMedicalInfoPageUrlPattern);
  });

  describe('UserMedicalInfo page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(userMedicalInfoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UserMedicalInfo page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/user-medical-info/new$'));
        cy.getEntityCreateUpdateHeading('UserMedicalInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userMedicalInfoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/user-medical-infos',
          body: userMedicalInfoSample,
        }).then(({ body }) => {
          userMedicalInfo = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/user-medical-infos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/user-medical-infos?page=0&size=20>; rel="last",<http://localhost/api/user-medical-infos?page=0&size=20>; rel="first"',
              },
              body: [userMedicalInfo],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(userMedicalInfoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UserMedicalInfo page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('userMedicalInfo');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userMedicalInfoPageUrlPattern);
      });

      it('edit button click should load edit UserMedicalInfo page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserMedicalInfo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userMedicalInfoPageUrlPattern);
      });

      it('edit button click should load edit UserMedicalInfo page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserMedicalInfo');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userMedicalInfoPageUrlPattern);
      });

      it('last delete button click should delete instance of UserMedicalInfo', () => {
        cy.intercept('GET', '/api/user-medical-infos/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('userMedicalInfo').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userMedicalInfoPageUrlPattern);

        userMedicalInfo = undefined;
      });
    });
  });

  describe('new UserMedicalInfo page', () => {
    beforeEach(() => {
      cy.visit(`${userMedicalInfoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UserMedicalInfo');
    });

    it('should create an instance of UserMedicalInfo', () => {
      cy.get(`[data-cy="usuarioId"]`).type('transmitter syndicate').should('have.value', 'transmitter syndicate');

      cy.get(`[data-cy="empresaId"]`).type('withdrawal Pound bluetooth').should('have.value', 'withdrawal Pound bluetooth');

      cy.get(`[data-cy="hypertension"]`).should('not.be.checked');
      cy.get(`[data-cy="hypertension"]`).click().should('be.checked');

      cy.get(`[data-cy="highGlucose"]`).should('not.be.checked');
      cy.get(`[data-cy="highGlucose"]`).click().should('be.checked');

      cy.get(`[data-cy="hiabetes"]`).should('not.be.checked');
      cy.get(`[data-cy="hiabetes"]`).click().should('be.checked');

      cy.get(`[data-cy="totalCholesterol"]`).type('45616').should('have.value', '45616');

      cy.get(`[data-cy="hdlCholesterol"]`).type('55802').should('have.value', '55802');

      cy.get(`[data-cy="medicalMainCondition"]`).type('up').should('have.value', 'up');

      cy.get(`[data-cy="medicalSecundaryCondition"]`).type('Queso').should('have.value', 'Queso');

      cy.get(`[data-cy="medicalMainMedication"]`).type('integrate').should('have.value', 'integrate');

      cy.get(`[data-cy="medicalSecundaryMedication"]`).type('ROI Algodón').should('have.value', 'ROI Algodón');

      cy.get(`[data-cy="medicalScore"]`).type('5318').should('have.value', '5318');

      cy.get(`[data-cy="endTime"]`).type('2023-04-09T22:24').blur().should('have.value', '2023-04-09T22:24');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        userMedicalInfo = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', userMedicalInfoPageUrlPattern);
    });
  });
});

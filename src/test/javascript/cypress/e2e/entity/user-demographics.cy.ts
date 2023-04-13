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

describe('UserDemographics e2e test', () => {
  const userDemographicsPageUrl = '/user-demographics';
  const userDemographicsPageUrlPattern = new RegExp('/user-demographics(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const userDemographicsSample = {};

  let userDemographics;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/user-demographics+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/user-demographics').as('postEntityRequest');
    cy.intercept('DELETE', '/api/user-demographics/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (userDemographics) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-demographics/${userDemographics.id}`,
      }).then(() => {
        userDemographics = undefined;
      });
    }
  });

  it('UserDemographics menu should load UserDemographics page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('user-demographics');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UserDemographics').should('exist');
    cy.url().should('match', userDemographicsPageUrlPattern);
  });

  describe('UserDemographics page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(userDemographicsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UserDemographics page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/user-demographics/new$'));
        cy.getEntityCreateUpdateHeading('UserDemographics');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userDemographicsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/user-demographics',
          body: userDemographicsSample,
        }).then(({ body }) => {
          userDemographics = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/user-demographics+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/user-demographics?page=0&size=20>; rel="last",<http://localhost/api/user-demographics?page=0&size=20>; rel="first"',
              },
              body: [userDemographics],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(userDemographicsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UserDemographics page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('userDemographics');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userDemographicsPageUrlPattern);
      });

      it('edit button click should load edit UserDemographics page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserDemographics');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userDemographicsPageUrlPattern);
      });

      it('edit button click should load edit UserDemographics page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserDemographics');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userDemographicsPageUrlPattern);
      });

      it('last delete button click should delete instance of UserDemographics', () => {
        cy.intercept('GET', '/api/user-demographics/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('userDemographics').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userDemographicsPageUrlPattern);

        userDemographics = undefined;
      });
    });
  });

  describe('new UserDemographics page', () => {
    beforeEach(() => {
      cy.visit(`${userDemographicsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UserDemographics');
    });

    it('should create an instance of UserDemographics', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Central parse').should('have.value', 'Central parse');

      cy.get(`[data-cy="empresaId"]`).type('Hogar Account de').should('have.value', 'Hogar Account de');

      cy.get(`[data-cy="gender"]`).type('hack').should('have.value', 'hack');

      cy.get(`[data-cy="dateOfBird"]`).type('2023-04-09').blur().should('have.value', '2023-04-09');

      cy.get(`[data-cy="age"]`).type('99594').should('have.value', '99594');

      cy.get(`[data-cy="country"]`).type('Croacia').should('have.value', 'Croacia');

      cy.get(`[data-cy="state"]`).type('JSON innovative').should('have.value', 'JSON innovative');

      cy.get(`[data-cy="city"]`).type('Albacete Oliviaside').should('have.value', 'Albacete Oliviaside');

      cy.get(`[data-cy="ethnicity"]`).type('COM').should('have.value', 'COM');

      cy.get(`[data-cy="income"]`).type('index granular').should('have.value', 'index granular');

      cy.get(`[data-cy="maritalStatus"]`).type('Relacciones Supervisor exploit').should('have.value', 'Relacciones Supervisor exploit');

      cy.get(`[data-cy="education"]`).type('Pelota synergize Genérico').should('have.value', 'Pelota synergize Genérico');

      cy.get(`[data-cy="endTime"]`).type('2023-04-09T13:19').blur().should('have.value', '2023-04-09T13:19');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        userDemographics = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', userDemographicsPageUrlPattern);
    });
  });
});

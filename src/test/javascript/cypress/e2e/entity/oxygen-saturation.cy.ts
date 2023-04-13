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

describe('OxygenSaturation e2e test', () => {
  const oxygenSaturationPageUrl = '/oxygen-saturation';
  const oxygenSaturationPageUrlPattern = new RegExp('/oxygen-saturation(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const oxygenSaturationSample = {};

  let oxygenSaturation;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/oxygen-saturations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/oxygen-saturations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/oxygen-saturations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (oxygenSaturation) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/oxygen-saturations/${oxygenSaturation.id}`,
      }).then(() => {
        oxygenSaturation = undefined;
      });
    }
  });

  it('OxygenSaturations menu should load OxygenSaturations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('oxygen-saturation');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('OxygenSaturation').should('exist');
    cy.url().should('match', oxygenSaturationPageUrlPattern);
  });

  describe('OxygenSaturation page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(oxygenSaturationPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create OxygenSaturation page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/oxygen-saturation/new$'));
        cy.getEntityCreateUpdateHeading('OxygenSaturation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/oxygen-saturations',
          body: oxygenSaturationSample,
        }).then(({ body }) => {
          oxygenSaturation = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/oxygen-saturations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/oxygen-saturations?page=0&size=20>; rel="last",<http://localhost/api/oxygen-saturations?page=0&size=20>; rel="first"',
              },
              body: [oxygenSaturation],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(oxygenSaturationPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details OxygenSaturation page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('oxygenSaturation');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationPageUrlPattern);
      });

      it('edit button click should load edit OxygenSaturation page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('OxygenSaturation');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationPageUrlPattern);
      });

      it('edit button click should load edit OxygenSaturation page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('OxygenSaturation');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationPageUrlPattern);
      });

      it('last delete button click should delete instance of OxygenSaturation', () => {
        cy.intercept('GET', '/api/oxygen-saturations/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('oxygenSaturation').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', oxygenSaturationPageUrlPattern);

        oxygenSaturation = undefined;
      });
    });
  });

  describe('new OxygenSaturation page', () => {
    beforeEach(() => {
      cy.visit(`${oxygenSaturationPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('OxygenSaturation');
    });

    it('should create an instance of OxygenSaturation', () => {
      cy.get(`[data-cy="usuarioId"]`).type('Yemen synthesizing').should('have.value', 'Yemen synthesizing');

      cy.get(`[data-cy="empresaId"]`).type('regional acompasada').should('have.value', 'regional acompasada');

      cy.get(`[data-cy="fieldOxigenSaturation"]`).type('96513').should('have.value', '96513');

      cy.get(`[data-cy="fieldSuplementalOxigenFlowRate"]`).type('53182').should('have.value', '53182');

      cy.get(`[data-cy="fieldOxigenTherapyAdministrationMode"]`).type('52108').should('have.value', '52108');

      cy.get(`[data-cy="fieldOxigenSaturationMode"]`).type('13719').should('have.value', '13719');

      cy.get(`[data-cy="fieldOxigenSaturationMeasurementMethod"]`).type('32281').should('have.value', '32281');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T11:57').blur().should('have.value', '2023-03-24T11:57');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        oxygenSaturation = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', oxygenSaturationPageUrlPattern);
    });
  });
});

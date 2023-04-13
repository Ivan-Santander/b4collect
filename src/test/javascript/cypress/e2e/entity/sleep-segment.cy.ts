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

describe('SleepSegment e2e test', () => {
  const sleepSegmentPageUrl = '/sleep-segment';
  const sleepSegmentPageUrlPattern = new RegExp('/sleep-segment(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sleepSegmentSample = {};

  let sleepSegment;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sleep-segments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sleep-segments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sleep-segments/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (sleepSegment) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sleep-segments/${sleepSegment.id}`,
      }).then(() => {
        sleepSegment = undefined;
      });
    }
  });

  it('SleepSegments menu should load SleepSegments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('sleep-segment');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SleepSegment').should('exist');
    cy.url().should('match', sleepSegmentPageUrlPattern);
  });

  describe('SleepSegment page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sleepSegmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SleepSegment page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/sleep-segment/new$'));
        cy.getEntityCreateUpdateHeading('SleepSegment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepSegmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/sleep-segments',
          body: sleepSegmentSample,
        }).then(({ body }) => {
          sleepSegment = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sleep-segments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/sleep-segments?page=0&size=20>; rel="last",<http://localhost/api/sleep-segments?page=0&size=20>; rel="first"',
              },
              body: [sleepSegment],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(sleepSegmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SleepSegment page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sleepSegment');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepSegmentPageUrlPattern);
      });

      it('edit button click should load edit SleepSegment page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SleepSegment');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepSegmentPageUrlPattern);
      });

      it('edit button click should load edit SleepSegment page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SleepSegment');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepSegmentPageUrlPattern);
      });

      it('last delete button click should delete instance of SleepSegment', () => {
        cy.intercept('GET', '/api/sleep-segments/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('sleepSegment').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sleepSegmentPageUrlPattern);

        sleepSegment = undefined;
      });
    });
  });

  describe('new SleepSegment page', () => {
    beforeEach(() => {
      cy.visit(`${sleepSegmentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SleepSegment');
    });

    it('should create an instance of SleepSegment', () => {
      cy.get(`[data-cy="usuarioId"]`).type('adaptador').should('have.value', 'adaptador');

      cy.get(`[data-cy="empresaId"]`).type('Macao Música').should('have.value', 'Macao Música');

      cy.get(`[data-cy="fieldSleepSegmentType"]`).type('22301').should('have.value', '22301');

      cy.get(`[data-cy="fieldBloodGlucoseSpecimenSource"]`).type('14556').should('have.value', '14556');

      cy.get(`[data-cy="startTime"]`).type('2023-03-23T19:11').blur().should('have.value', '2023-03-23T19:11');

      cy.get(`[data-cy="endTime"]`).type('2023-03-24T04:00').blur().should('have.value', '2023-03-24T04:00');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        sleepSegment = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', sleepSegmentPageUrlPattern);
    });
  });
});

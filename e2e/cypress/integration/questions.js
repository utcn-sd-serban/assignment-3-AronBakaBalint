describe("The question list", function() {

    it("should show 2 questions", function(){
        cy.visit("/#/question-list");

        cy.get('[data-cy="question"]').should("have.length", 2);
    });

    it("should add a new question", function(){
        cy.visit("/#/question-list");

        cy.get('[data-cy="add"]').click();

        cy.get('[data-cy="title"]').type("Cypress Test");

        cy.get('[data-cy="text"]').type("Wow, it works!");

        cy.get('[data-cy="tags"]').type("finally,cypress");

        cy.get('[data-cy="create"]').click();

        cy.get('[data-cy="question"]').should("have.length", 3);
    });

    it("should show 2 answers", function(){
        cy.visit("/#/question-list");

        cy.get('[data-cy="0"]').click();

        cy.get('[data-cy="answer"]').should("have.length", 2);
    });

    it("should show 2 results", function(){
        cy.visit("/#/question-list");

        cy.get('[data-cy="filterin"]').type("c+");

        cy.get('[data-cy="tagfilter"]').click();

        cy.get('[data-cy="filterRes"]').should("have.length", 1);
    });
    
    it("should not show anything", function(){
        cy.visit("/#/question-list");

        cy.get('[data-cy="1"]').click();

        cy.get('[data-cy="answer"]').should("have.length", 0);
    });

})
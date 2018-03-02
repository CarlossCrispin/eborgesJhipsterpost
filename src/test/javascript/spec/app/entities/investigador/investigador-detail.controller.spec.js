'use strict';

describe('Controller Tests', function() {

    describe('Investigador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInvestigador, MockGenero, MockGrado, MockDepartamento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInvestigador = jasmine.createSpy('MockInvestigador');
            MockGenero = jasmine.createSpy('MockGenero');
            MockGrado = jasmine.createSpy('MockGrado');
            MockDepartamento = jasmine.createSpy('MockDepartamento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Investigador': MockInvestigador,
                'Genero': MockGenero,
                'Grado': MockGrado,
                'Departamento': MockDepartamento
            };
            createController = function() {
                $injector.get('$controller')("InvestigadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eBorgesApp:investigadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

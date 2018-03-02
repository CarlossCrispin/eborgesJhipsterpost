'use strict';

describe('Controller Tests', function() {

    describe('TesisDirector Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTesisDirector, MockTesis, MockInvestigador, MockTipoAsesor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTesisDirector = jasmine.createSpy('MockTesisDirector');
            MockTesis = jasmine.createSpy('MockTesis');
            MockInvestigador = jasmine.createSpy('MockInvestigador');
            MockTipoAsesor = jasmine.createSpy('MockTipoAsesor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TesisDirector': MockTesisDirector,
                'Tesis': MockTesis,
                'Investigador': MockInvestigador,
                'TipoAsesor': MockTipoAsesor
            };
            createController = function() {
                $injector.get('$controller')("TesisDirectorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eBorgesApp:tesisDirectorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

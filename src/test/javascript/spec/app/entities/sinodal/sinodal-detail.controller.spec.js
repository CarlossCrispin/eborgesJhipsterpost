'use strict';

describe('Controller Tests', function() {

    describe('Sinodal Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSinodal, MockActa, MockTipoAsesor, MockInvestigador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSinodal = jasmine.createSpy('MockSinodal');
            MockActa = jasmine.createSpy('MockActa');
            MockTipoAsesor = jasmine.createSpy('MockTipoAsesor');
            MockInvestigador = jasmine.createSpy('MockInvestigador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Sinodal': MockSinodal,
                'Acta': MockActa,
                'TipoAsesor': MockTipoAsesor,
                'Investigador': MockInvestigador
            };
            createController = function() {
                $injector.get('$controller')("SinodalDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eBorgesApp:sinodalUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

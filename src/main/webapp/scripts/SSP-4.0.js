(function() {
  var AbstractService, AppController, Challenges, Demographics, EducationLevels, EducationalGoal, EducationalPlan, FundingSources, Student, StudentService,
    __hasProp = Object.prototype.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor; child.__super__ = parent.prototype; return child; };

  namespace('ssp.controller', {
    AppController: AppController = (function() {

      function AppController() {
        this.studentService = new ssp.service.StudentService("/ssp/web/example");
        this.student || (this.student = new ssp.model.Student);
      }

      AppController.prototype.loadStudent = function(studentId) {
        var _this = this;
        this.studentService.getStudent(studentId, {
          result: function(result) {
            return _this.student = result;
          },
          fault: function(fault) {
            return alert(fault.responseText);
          }
        });
      };

      return AppController;

    })()
  });

  namespace('ssp.service', {
    AbstractService: AbstractService = (function() {

      function AbstractService(baseURL) {
        this.baseURL = baseURL;
      }

      AbstractService.prototype.createURL = function(value) {
        var _ref;
        return "" + ((_ref = this.baseURL) != null ? _ref : '') + value;
      };

      return AbstractService;

    })()
  });

  namespace('ssp.service', {
    StudentService: StudentService = (function(_super) {

      __extends(StudentService, _super);

      function StudentService(baseURL) {
        StudentService.__super__.constructor.call(this, baseURL);
      }

      StudentService.prototype.getStudent = function(studentId, callbacks) {
        return $.ajax({
          url: this.createURL("/get"),
          dataType: "json",
          success: function(result) {
            var student;
            student = ssp.model.Student.createFromTransferObject(result);
            return callbacks != null ? typeof callbacks.result === "function" ? callbacks.result(student) : void 0 : void 0;
          },
          error: function(fault) {
            return callbacks != null ? typeof callbacks.fault === "function" ? callbacks.fault(fault) : void 0 : void 0;
          }
        });
      };

      return StudentService;

    })(ssp.service.AbstractService)
  });

  namespace('ssp.model', {
    Student: Student = (function() {

      function Student(studentId, firstName, middleInitial, lastName, uniqueSchoolUserID, birthDate, emailSchool, emailHome, homePhone, workPhone, cellPhone, address, city, state, zipCode) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.uniqueSchoolUserID = lastName;
        this.birthDate = birthDate;
        this.emailSchool = emailSchool;
        this.emailHome = emailHome;
        this.homePhone = homePhone;
        this.workPhone = workPhone;
        this.cellPhone = cellPhone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.demographics || (this.demographics = new ssp.model.studentintake.Demographics);
        this.educationalPlan || (this.educationalPlan = new ssp.model.studentintake.EducationalPlan);
        this.educationLevels || (this.educationLevels = new ssp.model.studentintake.EducationLevels);
        this.educationalGoal || (this.educationalGoal = new ssp.model.studentintake.EducationalGoal);
        this.fundingSources || (this.fundingSources = new ssp.model.studentintake.FundingSources);
        this.challenges || (this.challenges = new ssp.model.studentintake.Challenges);
      }

      Student.createFromTransferObject = function(studentTO) {
        return new Student(studentTO.studentId, studentTO.firstName, studentTO.middleInitial, studentTO.lastName, studentTO.uniqueSchoolUserID, studentTO.birthDate, studentTO.emailSchool, studentTO.emailHome, studentTO.homePhone, studentTO.workPhone, studentTO.cellPhone, studentTO.address, studentTO.city, studentTO.state, studentTO.zipCode);
      };

      return Student;

    })()
  });

  namespace('ssp.model.studentintake', {
    Challenges: Challenges = (function() {

      function Challenges(items) {
        this.items = [] | items;
      }

      return Challenges;

    })()
  });

  namespace('ssp.model.studentintake', {
    Demographics: Demographics = (function() {

      function Demographics(maritalStatus) {
        this.maritalStatus = maritalStatus;
      }

      return Demographics;

    })()
  });

  namespace('ssp.model.studentintake', {
    EducationLevels: EducationLevels = (function() {

      function EducationLevels(items) {
        this.items = [] | items;
      }

      return EducationLevels;

    })()
  });

  namespace('ssp.model.studentintake', {
    EducationalGoal: EducationalGoal = (function() {

      function EducationalGoal(careerGoal) {
        this.careerGoal = careerGoal;
      }

      return EducationalGoal;

    })()
  });

  namespace('ssp.model.studentintake', {
    EducationalPlan: EducationalPlan = (function() {

      function EducationalPlan(studentStatus) {
        this.studentStatus = studentStatus;
      }

      return EducationalPlan;

    })()
  });

  namespace('ssp.model.studentintake', {
    FundingSources: FundingSources = (function() {

      function FundingSources(items) {
        this.items = [] | items;
      }

      return FundingSources;

    })()
  });

}).call(this);

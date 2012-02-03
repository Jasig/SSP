namespace 'ssp.model'

	Student:
	
		class Student
		
			constructor: ( studentId, firstName, middleInitial, lastName, uniqueSchoolUserID, birthDate, emailSchool, emailHome, homePhone, workPhone, cellPhone, address, city, state, zipCode ) ->
				@studentId = studentId
				@firstName = firstName
				@middleInitial = middleInitial
				@lastName = lastName
				@uniqueSchoolUserID = lastName
				@birthDate = birthDate
				@emailSchool = emailSchool
				@emailHome = emailHome
				@homePhone = homePhone
				@workPhone = workPhone
				@cellPhone = cellPhone
				@address = address
				@city = city
				@state = state
				@zipCode = zipCode
				
				@demographics ||= new ssp.model.studentintake.Demographics
				@educationalPlan ||= new ssp.model.studentintake.EducationalPlan
				@educationLevels ||= new ssp.model.studentintake.EducationLevels
				@educationalGoal ||= new ssp.model.studentintake.EducationalGoal
				@fundingSources ||= new ssp.model.studentintake.FundingSources
				@challenges ||= new ssp.model.studentintake.Challenges
				
				
			@createFromTransferObject: ( studentTO ) ->
				return new Student( studentTO.studentId, studentTO.firstName, studentTO.middleInitial, studentTO.lastName, studentTO.uniqueSchoolUserID, studentTO.birthDate, studentTO.emailSchool, studentTO.emailHome, studentTO.homePhone, studentTO.workPhone, studentTO.cellPhone, studentTO.address, studentTO.city, studentTO.state, studentTO.zipCode )
			
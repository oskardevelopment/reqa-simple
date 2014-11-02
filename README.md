ReQA - simple
===========

Requirements Quality Assurance (ReQA) allows teams to verify their requirements and connect tests with the requirements they fulfill.
ReQA simple is a simple ReQA edition without visualization and is compatible for Java 1.7.

ReQA is to be used whenever a developer is required to track, visualize or measure requirements and the tests connected to said requirements.
ReQA is also fit to be used by managers that want to be able to visualize how developer's work have progressed.

Use cases
===========

ReQA simple uses a test engine that's built upon JUnitCore to verify requirements and tests.

```java
// Example of how ReqaTester allows testing all tests in a project.
Session session = new ReqaTester().sessionRun();

// Example of how ReqaTester allows testing all tests in the current package.
Session session = new ReqaTester(new CurrentPackageScanner()).sessionRun();
		
// Example of how ReqaTester allows testing all tests in a specific package.
Session session = new ReqaTester(new PackageScanner(ReqaTesterExample.class.getPackage().getName())).sessionRun();

// Example of how ReqaTester allows testing all tests in a class.
Session session = new ReqaTester().sessionRun(ReqaTesterExample.class);
```

The following is a simplified example of a ReQA test scenario.

```java
public class ReqaTesterExample {
	
	@Test
	@Verifiable(verifies = "TEST1EX", gist = "A test example to show the ReQA functionality.")
	public void testExample() {
		// given:
		int one = 1;
		int two = 2;
		int three = 3;
		
		// when:
		int sum = one + two;
		
		// then:
		assertEquals(three, sum);
	}
	
	public static void main(String[] e) {
		// Simple example of ReqaTester and how to use the outcome.
		Session session = new ReqaTester().sessionRun(ReqaTesterExample.class);

		System.out.println(session.getVerifies().size()); // 1
		System.out.println(session.getTests().size()); // 1

		Verified verified = session.getVerifies().get(0);
		System.out.println(verified.getTested().size()); // 1
		System.out.println(verified.id); // TEST1EX
		System.out.println(verified.getTested().get(0).test); // ReqaTesterExample.testExample
	}
	
}
```

ReQA tracks and measures the testing and the requirements, allowing developers to visualize their progress.

![Alt text](/files/duration.png?raw=true)

![Alt text](/files/reqaCount.png?raw=true)

ReQA allows custom presentation of the verification process, thereby giving developers the means to present tests in an expressive manner.

```java
new ReqaTester(new PackageScanner(ScannerTest.class.getPackage().getName()), new Json(), new SessionSaver()).sessionRun();
/*
[
  {
    "sessionStart": "2014-11-02T16:38:56:780+01",
    "sessionEnd": "2014-11-02T16:38:56:811+01",
    "verifies": [
      {
        "id": "Scanner can get tests in current package",
        "isVerified": true,
        "tests": [
          {
            "test": "ScannerTest.scanningCurrentPackageTestsCurrentPackage",
            "gist": "With CurrentPackageScanner, then all tested will be in current package.",
            "startedAt": "2014-11-02T16:38:56:780+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 16,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "Scanner can get tests in a specific package",
        "isVerified": true,
        "tests": [
          {
            "test": "ScannerTest.scanningPackageTestsOnlyPackage",
            "gist": "PackageScanner scanning a specific package, gives only tested in said package.",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "Verified session stores verifies",
        "isVerified": true,
        "tests": [
          {
            "test": "SessionTest.verifiedSessionHaveVerified",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "Session stores tested",
        "isVerified": true,
        "tests": [
          {
            "test": "SessionTest.verifiedSessionHaveVerified",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 0,
            "isSuccessful": true
          },
          {
            "test": "SessionTest.testedCanBeUpdated",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 0,
            "isSuccessful": true
          },
          {
            "test": "SessionTest.sessionStartCanBeUpdated",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 0,
            "isSuccessful": true
          },
          {
            "test": "SessionTest.testedCanBeAdded",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "Tested can be updated",
        "isVerified": true,
        "tests": [
          {
            "test": "TestedTest.verifyThatTestedIsUpdated",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "Tested describes the test",
        "isVerified": true,
        "tests": [
          {
            "test": "TestedTest.verifyTestedVerifies",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "Tested tracks measurements",
        "isVerified": true,
        "tests": [
          {
            "test": "TestedTest.verifyDurationIsFinishedSubtractedByStarted",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:796+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "ReqaListener handles TestListeners during a test session",
        "isVerified": true,
        "tests": [
          {
            "test": "ListenerTest.verifyReqaListener",
            "startedAt": "2014-11-02T16:38:56:796+01",
            "finishedAt": "2014-11-02T16:38:56:811+01",
            "durationInMillis": 15,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "SessionListener manages the Session during a test\u0027s life cycle",
        "isVerified": true,
        "tests": [
          {
            "test": "ListenerTest.verifySessionListenersManagingOfSession",
            "startedAt": "2014-11-02T16:38:56:811+01",
            "finishedAt": "2014-11-02T16:38:56:811+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "Verified tracks the duration of all tested",
        "isVerified": true,
        "tests": [
          {
            "test": "VerifiedTest.verifyDuration",
            "startedAt": "2014-11-02T16:38:56:811+01",
            "finishedAt": "2014-11-02T16:38:56:811+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "Verified have equals and hashCode",
        "isVerified": true,
        "tests": [
          {
            "test": "VerifiedTest.verifyEquals",
            "startedAt": "2014-11-02T16:38:56:811+01",
            "finishedAt": "2014-11-02T16:38:56:811+01",
            "durationInMillis": 0,
            "isSuccessful": true
          },
          {
            "test": "VerifiedTest.verifyHashCode",
            "startedAt": "2014-11-02T16:38:56:811+01",
            "finishedAt": "2014-11-02T16:38:56:811+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      },
      {
        "id": "Verified have list of all tested",
        "isVerified": true,
        "tests": [
          {
            "test": "VerifiedTest.verifyTested",
            "startedAt": "2014-11-02T16:38:56:811+01",
            "finishedAt": "2014-11-02T16:38:56:811+01",
            "durationInMillis": 0,
            "isSuccessful": true
          }
        ]
      }
    ],
    "tests": [
      {
        "test": "ScannerTest.scanningCurrentPackageTestsCurrentPackage",
        "gist": "With CurrentPackageScanner, then all tested will be in current package.",
        "startedAt": "2014-11-02T16:38:56:780+01",
        "finishedAt": "2014-11-02T16:38:56:796+01",
        "durationInMillis": 16,
        "isSuccessful": true
      },
      {
        "test": "ScannerTest.scanningPackageTestsOnlyPackage",
        "gist": "PackageScanner scanning a specific package, gives only tested in said package.",
        "startedAt": "2014-11-02T16:38:56:796+01",
        "finishedAt": "2014-11-02T16:38:56:796+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "SessionTest.verifiedSessionHaveVerified",
        "startedAt": "2014-11-02T16:38:56:796+01",
        "finishedAt": "2014-11-02T16:38:56:796+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "SessionTest.testedCanBeUpdated",
        "startedAt": "2014-11-02T16:38:56:796+01",
        "finishedAt": "2014-11-02T16:38:56:796+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "SessionTest.sessionStartCanBeUpdated",
        "startedAt": "2014-11-02T16:38:56:796+01",
        "finishedAt": "2014-11-02T16:38:56:796+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "SessionTest.testedCanBeAdded",
        "startedAt": "2014-11-02T16:38:56:796+01",
        "finishedAt": "2014-11-02T16:38:56:796+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "TestedTest.verifyThatTestedIsUpdated",
        "startedAt": "2014-11-02T16:38:56:796+01",
        "finishedAt": "2014-11-02T16:38:56:796+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "TestedTest.verifyTestedVerifies",
        "startedAt": "2014-11-02T16:38:56:796+01",
        "finishedAt": "2014-11-02T16:38:56:796+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "TestedTest.verifyDurationIsFinishedSubtractedByStarted",
        "startedAt": "2014-11-02T16:38:56:796+01",
        "finishedAt": "2014-11-02T16:38:56:796+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "ListenerTest.verifyReqaListener",
        "startedAt": "2014-11-02T16:38:56:796+01",
        "finishedAt": "2014-11-02T16:38:56:811+01",
        "durationInMillis": 15,
        "isSuccessful": true
      },
      {
        "test": "ListenerTest.verifySessionListenersManagingOfSession",
        "startedAt": "2014-11-02T16:38:56:811+01",
        "finishedAt": "2014-11-02T16:38:56:811+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "VerifiedTest.verifyDuration",
        "startedAt": "2014-11-02T16:38:56:811+01",
        "finishedAt": "2014-11-02T16:38:56:811+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "VerifiedTest.verifyEquals",
        "startedAt": "2014-11-02T16:38:56:811+01",
        "finishedAt": "2014-11-02T16:38:56:811+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "VerifiedTest.verifyTested",
        "startedAt": "2014-11-02T16:38:56:811+01",
        "finishedAt": "2014-11-02T16:38:56:811+01",
        "durationInMillis": 0,
        "isSuccessful": true
      },
      {
        "test": "VerifiedTest.verifyHashCode",
        "startedAt": "2014-11-02T16:38:56:811+01",
        "finishedAt": "2014-11-02T16:38:56:811+01",
        "durationInMillis": 0,
        "isSuccessful": true
      }
    ]
  }
]
*/
```


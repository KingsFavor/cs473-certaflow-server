## How to use?

### 1.  Clone this repository
```bash
git clone https://github.com/KingsFavor/cs473-certaflow-server.git
```


### 2.  Environment Variable Setting and Run
#### Variables for congestion level decision
- CURRENT_VIEW_WEIGHT
- OFFICIAL_INFO_WEIGHT
- USER_FEEDBACK_WEIGHT
- NEAR_USER_WEIGHT

#### Variables for contribution level decision
- SURVEY_CONTRIBUTION_POINT
- TIP_LIKE_POINT
- MESSAGE_LIKE_POINT

#### Example
```bash
cd cs473-certaflow-server
```
```bash
export CURRENT_VIEW_WEIGHT=1 &&
export OFFICIAL_INFO_WEIGHT=10 &&
export USER_FEEDBACK_WEIGHT=3 &&
export NEAR_USER_WEIGHT=2 &&
export SURVEY_CONTRIBUTION_POINT=5 &&
export TIP_LIKE_POINT=15 &&
export MESSAGE_LIKE_POINT=10 &&
./mvnw spring-boot:run
```

<br />

## Project Hierarchy

### Tree [ PROJECT_ROOT/src/main/java/com/cs473/cs473server ]

```bash
./
├── Cs473ServerApplication.java
├── domain/
│   ├── chat/
│   │   ├── controller/
│   │   │   └── ChatController.java
│   │   └── service/
│   │       ├── ChatService.java
│   │       └── impl/
│   │           └── ChatServiceImpl.java
│   ├── congestion/
│   │   ├── controller/
│   │   │   └── CongestionController.java
│   │   └── service/
│   │       ├── CongestionService.java
│   │       └── impl/
│   │           └── CongestionServiceImpl.java
│   ├── location/
│   │   ├── controller/
│   │   │   └── LocationController.java
│   │   └── service/
│   │       ├── LocationService.java
│   │       └── impl/
│   │           └── LocationServiceImpl.java
│   ├── login/
│   │   ├── controller/
│   │   │   └── LoginController.java
│   │   └── service/
│   │       ├── LoginService.java
│   │       └── impl/
│   │           └── LoginServiceImpl.java
│   └── user/
│       ├── controller/
│       │   └── UserController.java
│       └── service/
│           ├── UserService.java
│           └── impl/
│               └── UserServiceImpl.java
└── global/
    ├── data/
    │   ├── dto/
    │   │   ├── ChatDto.java
    │   │   ├── ChatMessageDto.java
    │   │   ├── CongestionFeedbackDto.java
    │   │   ├── CongestionOfficialDto.java
    │   │   ├── LocationCategoryDto.java
    │   │   ├── LocationDto.java
    │   │   ├── OnPlanDto.java
    │   │   ├── TipDto.java
    │   │   ├── UserDto.java
    │   │   ├── UserMessageLikeDto.java
    │   │   └── UserTipLikeDto.java
    │   ├── entity/
    │   │   ├── Chat.java
    │   │   ├── ChatMessage.java
    │   │   ├── CongestionFeedback.java
    │   │   ├── CongestionOfficial.java
    │   │   ├── Location.java
    │   │   ├── LocationCategory.java
    │   │   ├── OnPlan.java
    │   │   ├── Tip.java
    │   │   ├── User.java
    │   │   ├── UserMessageLike.java
    │   │   └── UserTipLike.java
    │   └── repository/
    │       ├── ChatMessageRepository.java
    │       ├── ChatRepository.java
    │       ├── CongestionFeedbackRepository.java
    │       ├── CongestionOfficialRepository.java
    │       ├── LocationRepository.java
    │       ├── OnPlanRepository.java
    │       ├── TipRepository.java
    │       ├── UserMessageLikeRepository.java
    │       ├── UserRepository.java
    │       └── UserTipLikeRepository.java
    ├── scheduler/
    │   └── DataCleanScheduler.java
    └── service/
        ├── CongestionLevelCoreService.java
        ├── ContributionLevelCoreService.java
        ├── DataCheckService.java
        └── ResponseBodyFormatService.java
```
## NOTE
- The project is mainly based on MVC pattern.


## domain/controller
- These classes handle endpoint request from Spring dispatcher.
- They will call service components.

## domain/service
- These classes handle some logics called by controllers.
- Main program logics are handled in here.
- They will call repository components.

## global/data/dto
- Data abstraction classes.

## global/data/entity
- Data entities for Jpa Repository.

## global/data/repository
- Extending Jpa Repository class.
- For security and convenience, this project uses Jpa queries.

## global/scheduler
- Spring Scheduler classes.
- These classes will handle scheduled logics. (e.g. clean-up database)

## global/service
- Global service classes.
- ```CongestionLevelCoreService.java``` and ```ContributionLevelCoreService.java``` will handle our main calculations.


# ERD (Tentative)

- Link : [ERD cloud](https://www.erdcloud.com/d/hddQX3gGYH9h4jFM3)
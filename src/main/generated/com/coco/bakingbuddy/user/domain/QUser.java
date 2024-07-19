package com.coco.bakingbuddy.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -71714215L;

    public static final QUser user = new QUser("user");

    public final com.coco.bakingbuddy.global.domain.QBaseTime _super = new com.coco.bakingbuddy.global.domain.QBaseTime(this);

    public final BooleanPath activated = createBoolean("activated");

    public final ListPath<com.coco.bakingbuddy.alarm.domain.Alarm, com.coco.bakingbuddy.alarm.domain.QAlarm> alarms = this.<com.coco.bakingbuddy.alarm.domain.Alarm, com.coco.bakingbuddy.alarm.domain.QAlarm>createList("alarms", com.coco.bakingbuddy.alarm.domain.Alarm.class, com.coco.bakingbuddy.alarm.domain.QAlarm.class, PathInits.DIRECT2);

    public final BooleanPath alarmYn = createBoolean("alarmYn");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final ListPath<com.coco.bakingbuddy.recipe.domain.Directory, com.coco.bakingbuddy.recipe.domain.QDirectory> directories = this.<com.coco.bakingbuddy.recipe.domain.Directory, com.coco.bakingbuddy.recipe.domain.QDirectory>createList("directories", com.coco.bakingbuddy.recipe.domain.Directory.class, com.coco.bakingbuddy.recipe.domain.QDirectory.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath picture = createString("picture");

    public final StringPath profile = createString("profile");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final StringPath provideId = createString("provideId");

    public final StringPath provider = createString("provider");

    public final ListPath<com.coco.bakingbuddy.search.domain.RecentSearch, com.coco.bakingbuddy.search.domain.QRecentSearch> recentSearches = this.<com.coco.bakingbuddy.search.domain.RecentSearch, com.coco.bakingbuddy.search.domain.QRecentSearch>createList("recentSearches", com.coco.bakingbuddy.search.domain.RecentSearch.class, com.coco.bakingbuddy.search.domain.QRecentSearch.class, PathInits.DIRECT2);

    public final ListPath<com.coco.bakingbuddy.recipe.domain.Recipe, com.coco.bakingbuddy.recipe.domain.QRecipe> recipes = this.<com.coco.bakingbuddy.recipe.domain.Recipe, com.coco.bakingbuddy.recipe.domain.QRecipe>createList("recipes", com.coco.bakingbuddy.recipe.domain.Recipe.class, com.coco.bakingbuddy.recipe.domain.QRecipe.class, PathInits.DIRECT2);

    public final StringPath role = createString("role");

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}


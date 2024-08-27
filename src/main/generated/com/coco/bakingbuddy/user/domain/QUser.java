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

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.coco.bakingbuddy.file.domain.UserProfileImageFile, com.coco.bakingbuddy.file.domain.QUserProfileImageFile> imageFiles = this.<com.coco.bakingbuddy.file.domain.UserProfileImageFile, com.coco.bakingbuddy.file.domain.QUserProfileImageFile>createList("imageFiles", com.coco.bakingbuddy.file.domain.UserProfileImageFile.class, com.coco.bakingbuddy.file.domain.QUserProfileImageFile.class, PathInits.DIRECT2);

    public final StringPath introduction = createString("introduction");

    public final SetPath<com.coco.bakingbuddy.like.domain.Like, com.coco.bakingbuddy.like.domain.QLike> likedRecipes = this.<com.coco.bakingbuddy.like.domain.Like, com.coco.bakingbuddy.like.domain.QLike>createSet("likedRecipes", com.coco.bakingbuddy.like.domain.Like.class, com.coco.bakingbuddy.like.domain.QLike.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.coco.bakingbuddy.product.domain.Product, com.coco.bakingbuddy.product.domain.QProduct> products = this.<com.coco.bakingbuddy.product.domain.Product, com.coco.bakingbuddy.product.domain.QProduct>createList("products", com.coco.bakingbuddy.product.domain.Product.class, com.coco.bakingbuddy.product.domain.QProduct.class, PathInits.DIRECT2);

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final StringPath provideId = createString("provideId");

    public final StringPath provider = createString("provider");

    public final ListPath<com.coco.bakingbuddy.recipe.domain.Recipe, com.coco.bakingbuddy.recipe.domain.QRecipe> recipes = this.<com.coco.bakingbuddy.recipe.domain.Recipe, com.coco.bakingbuddy.recipe.domain.QRecipe>createList("recipes", com.coco.bakingbuddy.recipe.domain.Recipe.class, com.coco.bakingbuddy.recipe.domain.QRecipe.class, PathInits.DIRECT2);

    public final ListPath<com.coco.bakingbuddy.admin.domain.Report, com.coco.bakingbuddy.admin.domain.QReport> reports = this.<com.coco.bakingbuddy.admin.domain.Report, com.coco.bakingbuddy.admin.domain.QReport>createList("reports", com.coco.bakingbuddy.admin.domain.Report.class, com.coco.bakingbuddy.admin.domain.QReport.class, PathInits.DIRECT2);

    public final EnumPath<com.coco.bakingbuddy.user.service.RoleType> role = createEnum("role", com.coco.bakingbuddy.user.service.RoleType.class);

    public final ListPath<com.coco.bakingbuddy.search.domain.SearchRecord, com.coco.bakingbuddy.search.domain.QSearchRecord> searchRecords = this.<com.coco.bakingbuddy.search.domain.SearchRecord, com.coco.bakingbuddy.search.domain.QSearchRecord>createList("searchRecords", com.coco.bakingbuddy.search.domain.SearchRecord.class, com.coco.bakingbuddy.search.domain.QSearchRecord.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public final ComparablePath<java.util.UUID> uuid = createComparable("uuid", java.util.UUID.class);

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


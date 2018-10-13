var CollectionsAndFiles = new JavaImporter(
    Packages.game_objects,
    java.lang.System
);with(CollectionsAndFiles) {


    game.addCollectableModel("biggerBonus.png", new Effect()
    {
        effect: function (p) {
            this.p=p;
            var body = p.getB();
            body.setHeight(body.getHeight() * 1.5);
            body.setWidth(body.getWidth() * 1.5);
        },
        update: function (c) {
            var currTime = System.nanoTime() / 1000000000.0;
            if ((currTime - c.getTime()) > 10.0) {
                if(this.p==undefined) {
                    game.removeCollectable(c);
                }else{
                    c.getEffect().end();
                }
            }
        },
        end: function () {
            var body = this.p.getB();
            var originBody = this.p.getOriginBody();
            body.setHeight(originBody.getHeight());
            body.setWidth(originBody.getWidth());
            this.p.collectableEnd();
            this.p=undefined
        }


    });

    game.addCollectableModel("smallerBonus.png", new Effect()
    {
        effect: function (p) {
            this.p=p;
            var body = p.getB();
            body.setHeight(body.getHeight() * 0.5);
            body.setWidth(body.getWidth() * 0.5);
        },
        update: function (c) {
            var currTime = System.nanoTime() / 1000000000.0;
            if ((currTime - c.getTime()) > 10.0) {
                if(this.p==undefined) {
                    game.removeCollectable(c);
                }else{
                    c.getEffect().end();
                }
            }
        },
        end: function () {
            var body = this.p.getB();
            var originBody = this.p.getOriginBody();
            body.setHeight(originBody.getHeight());
            body.setWidth(originBody.getWidth());
            this.p.collectableEnd();
            this.p=undefined;
        }

    });

    game.addCollectableModel("fasterBonus.png", new Effect()
    {
        effect: function (p) {
            this.p=p;
            this.p.setSpeed(2);
        },
        update: function (c) {
            var currTime = System.nanoTime() / 1000000000.0;
            if ((currTime - c.getTime()) > 10.0) {
                if(this.p==undefined) {
                    game.removeCollectable(c);
                }else{
                    c.getEffect().end();
                }
            }
        },
        end: function () {
            this.p.setSpeed(1);
            this.p.collectableEnd();
            this.p=undefined
        }


    });

    game.addCollectableModel("slowerBonus.png", new Effect()
    {
        effect: function (p) {
            this.p=p;
            this.p.setSpeed(0.5);
        },
        update: function (c) {
            var currTime = System.nanoTime() / 1000000000.0;
            if ((currTime - c.getTime()) > 10.0) {
                if(this.p==undefined) {
                    game.removeCollectable(c);
                }else{
                    c.getEffect().end();
                }
            }
        },
        end: function () {
            this.p.setSpeed(1);
            this.p.collectableEnd();
            this.p=undefined
        }


    });
}

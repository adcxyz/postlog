+ String {
	post {
		PostOffice.add(this);
		if (PostLog.logPosts) { PostLog.write(this) };
		this.prPost;
	}
	postln {
		PostOffice.add(this ++ "\n");
		if (PostLog.logPosts) { PostLog.write(this ++ "\n") };
		this.prPostln;
	}

	prPostln { _PostLine }
	prPost { _PostString }
}

+ Object {
	log { PostLog.write(this) }
}
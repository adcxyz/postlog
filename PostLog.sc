
PostLog {
	classvar <>dirPath, <filePath, <file;
	classvar <>on = false, <>tracing = false, <>logPosts = true;
	classvar <appAsDir, <appName, <appDir;

	*initClass {
		appAsDir = Platform.resourceDir.dirname.dirname;
		appDir = appAsDir.dirname;
		appName = appAsDir.basename.splitext[0];

		dirPath = appDir +/+ "Logs_" ++ appName;
		if (on) { this.start };
	}

	*checkLogDir { |num = 5|
		num.do {
			if (dirPath.pathMatch.isEmpty) {
				"% : making dir at '%'".postf(thisMethod, dirPath);
				dirPath.mkdir;
			} {
				^true
			}
		};
		"% : could not find or create logFolder at '%'.\n"
		.postf(thisMethod, dirPath);
		^false
	}

	*start {
		on = true;
		if (this.checkLogDir) {
			filePath =dirPath +/+ Date.getDate.stamp ++ "_log.txt";
			file = File(filePath, "a");
		};
		"% started logging at: %.\n".postf(this, filePath);
		ShutDown.add(this);
	}

	*stop {
		"% stopped logging at: %.\n".postf(this, filePath);
		if (file.notNil and: { file.isOpen }) { file.close };
		on = false;
		ShutDown.remove(this);
	}

	*doOnShutDown { this.stop }

	*openDir { dirPath.openOS }

	*open {
		if (filePath.notNil) {
			filePath.openOS
		} {
			"%: no filePath, cannot open file.".postf(this);
		}
	}

	// try always closing and appending
	*write { |str|
		var logStr;
		defer {
			if (on and: { file.notNil }) {
				if (file.isOpen.not) {
					file = File(filePath, "a");
				};
				logStr = Date.getDate.stamp + str ++ "\n";
				if (tracing) { "logging: %".format(logStr).prPostln; };
				file.write(logStr);
				file.close;
			}
		}
	}

	*writeIfOpen { |str|
		var logStr;
		defer {
			if (on and: { file.notNil and: { file.isOpen }}) {
				logStr = Date.getDate.stamp + str ++ "\n";
				if (tracing) { "logging: %".format(logStr).prPostln; };
				file.write(logStr);
		} }
	}
}

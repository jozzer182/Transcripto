// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "CoreUI",
    platforms: [
        .iOS(.v16)
    ],
    products: [
        .library(name: "CoreUI", targets: ["CoreUI"])
    ],
    targets: [
        .target(name: "CoreUI", dependencies: [])
    ]
)
